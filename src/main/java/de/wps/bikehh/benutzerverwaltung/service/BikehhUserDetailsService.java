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
    private AuthService _authService;

    @Autowired
    public BikehhUserDetailsService(UserAuthenticationRepository userAuthenticationRepository, VerifyDetailsService verifyDetailsService,AuthService authService) {
        this._userAuthenticationRepository = userAuthenticationRepository;
        this._verifyDetailsService = verifyDetailsService;
        this._authService = authService;
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

    public UserDetailsResponseModel getCurrentUser(User user) throws ApiRequestException {
        return new UserDetailsResponseModel(user);
    }

    public void updateUser(User user, UpdateUserDetailsRequestModel userUpdate) throws ApiRequestException {
        if (user.getIsLocked()) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }

        user.setEmailAddress(userUpdate.getEmail());
        user.setPrivacySetting(userUpdate.getPrivacySetting());
        _userAuthenticationRepository.save(user);
    }

    public void deleteUser(User user) {
        _authService.logoutAllSession(user);
        _userAuthenticationRepository.delete(user);
    }

    public void updatePassword(User user, String passwordOld, String passwordNew) throws ApiRequestException {
        if(!Validation.isPasswordValid(passwordNew)){
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        BikehhPasswordEncoderService encoder = new BikehhPasswordEncoderService();
        String hashedPasswordOld = encoder.encodePassword(passwordOld);
        if(!encoder.matches(hashedPasswordOld, user.getEncryptedPassword()))  {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }
        user.setEncryptedPassword(encoder.encodePassword(passwordNew));
        _userAuthenticationRepository.save(user);
    }

    public List<User> retrieveUsers() {
        List<User> users = new ArrayList<>();
        for (User user : _userAuthenticationRepository.findAll()) {
            users.add(user);
        }

        return users;
    }

    public User getUserById(Long id) {
        if (!_userAuthenticationRepository.existsById(id)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = _userAuthenticationRepository.findById(id).orElse(null);

        return user;
    }

    public void deleteUserById(Long id) {
        if (!_userAuthenticationRepository.existsById(id)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        _userAuthenticationRepository.deleteById(id);
    }

    public User updateUserById(Long id, User updatedUser) {
        if (!_userAuthenticationRepository.existsById(id)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = _userAuthenticationRepository.findById(id).orElse(null);
        updatedUser.setId(user.getId());

        return _userAuthenticationRepository.save(updatedUser);
    }
}
