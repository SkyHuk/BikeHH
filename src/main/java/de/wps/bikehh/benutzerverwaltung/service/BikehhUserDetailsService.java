package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.List;

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;

@Service
public class BikehhUserDetailsService implements UserDetailsService {

    private UserAuthenticationRepository _userAuthenticationRepository;

    @Autowired
    public BikehhUserDetailsService(UserAuthenticationRepository userAuthenticationRepository) {
        this._userAuthenticationRepository = userAuthenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
           return null;
        }

        User user = _userAuthenticationRepository.findByEmailAddress(email);

        if (user.getIsLocked()) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }
        return new BikehhUserDetails(user, email, user.getEncryptedPassword(), createAuthorities(user));
    }

    public void createUser(User user) throws ApiRequestException {
        if (_userAuthenticationRepository.existsByEmailAddress(user.getEmailAddress())) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }
        _userAuthenticationRepository.save(user);
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

	public void updateUser(User user, UpdateUserDetailsRequestModel userUpdate) {
        
        if (user.getIsLocked()) {
            throw new LockedException("Nutzer ist gesperrt");
        }
        if (_userAuthenticationRepository.existsByEmailAddress(userUpdate.getEmail())){
            throw new UsernameNotFoundException(userUpdate.getEmail()+"bereits vergeben");
        }
        user.setEmailAddress(userUpdate.getEmail());
        user.setPrivacySetting(userUpdate.getPrivacySetting());
        _userAuthenticationRepository.save(user);
    }

	public void deleteUser(User user) {
        if(!(_userAuthenticationRepository.existsByEmailAddress(user.getEmailAddress()))){
            throw new UsernameNotFoundException(user.getId() + "existiert nicht!");
        }
        _userAuthenticationRepository.delete(user);
	}
}
