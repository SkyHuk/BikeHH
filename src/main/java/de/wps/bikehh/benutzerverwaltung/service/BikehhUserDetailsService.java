package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.util.Validation;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikehhUserDetailsService implements UserDetailsService {

    private UserAuthenticationRepository _userAuthenticationRepository;
    private VerifyDetailsService _verifyDetailsService;

    @Autowired
    public BikehhUserDetailsService(UserAuthenticationRepository userAuthenticationRepository, VerifyDetailsService verifyDetailsService) {
        this._userAuthenticationRepository = userAuthenticationRepository;
        this._verifyDetailsService = verifyDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = _userAuthenticationRepository.findByEmailAddress(email);

        if (user.getIsLocked()) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }
        return new BikehhUserDetails(user, email, user.getEncryptedPassword(), createAuthorities(user));
    }

    public void createUser(String email, String password) throws ApiRequestException {
        if (!Validation.isEmailValid(email) || !Validation.isPasswordValid(password)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }
        if (_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = new User(email, password);
        user.setRole(Roles.ROLE_USER);

        BikehhPasswordEncoderService encoder = new BikehhPasswordEncoderService();
        user.setEncryptedPassword(encoder.encodePassword(password));

        _userAuthenticationRepository.save(user);
        _verifyDetailsService.requestVerificationMail(user.getEmailAddress());
    }

    private String[] createAuthorities(User user) {
        List<String> authorities = new ArrayList<>(3);

        switch (user.getRole()) {
            case Roles.ROLE_ADMIN:
                authorities.add(Roles.ROLE_ADMIN);
            case Roles.ROLE_USER:
                authorities.add(Roles.ROLE_USER);
                break;
            default:
                throw new RuntimeException("Unbekannte Role (" + user.getRole() + ") von User " + user.getEmailAddress());
        }

        return authorities.toArray(new String[authorities.size()]);
    }

    public UserDetailsResponseModel getCurrentUser(Authentication auth) throws ApiRequestException {
        User user = (User) auth.getPrincipal();
        return new UserDetailsResponseModel(user);
    }

    public void updateUser(Authentication auth, UpdateUserDetailsRequestModel userUpdate) throws ApiRequestException {
        User user = (User) auth.getPrincipal();
        if (user.getIsLocked()) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }

        user.setEmailAddress(userUpdate.getEmail());
        user.setPrivacySetting(userUpdate.getPrivacySetting());
        _userAuthenticationRepository.save(user);
    }

    public void deleteUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        _userAuthenticationRepository.delete(user);

    }
}
