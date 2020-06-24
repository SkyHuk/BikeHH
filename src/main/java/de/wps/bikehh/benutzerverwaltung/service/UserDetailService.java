package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUsersDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserDetailService implements UserDetailsService {

    private UserAuthenticationRepository _userAuthenticationRepository;
    private VerifyDetailService _verifyDetailService;
    private PasswordDetailService _passwordDetailService;
    private AuthService _authService;

    @Autowired
    public UserDetailService(UserAuthenticationRepository userAuthenticationRepository, VerifyDetailService verifyDetailService, PasswordDetailService passwordDetailService, AuthService authService) {
        this._userAuthenticationRepository = userAuthenticationRepository;
        this._verifyDetailService = verifyDetailService;
        this._authService = authService;
        this._passwordDetailService = passwordDetailService;
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


    public void createUser(String email, String password) throws ApiRequestException {
        createUserEntity(email, password, Roles.ROLE_USER);
    }

    public void createAdmin(String email, String password) {
        createUserEntity(email, password, Roles.ROLE_ADMIN);
    }

    private void createUserEntity(String email, String password, String role) throws ApiRequestException {

        if (_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = new User(email, password);
        user.setRole(role);

        PasswordEncoderService encoder = new PasswordEncoderService();
        user.setEncryptedPassword(encoder.encodePassword(password));

        _userAuthenticationRepository.save(user);
        _verifyDetailService.requestVerificationMail(user.getEmailAddress());
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
        Long userId = user.getId();

        _authService.logoutAllSession(userId);
        _verifyDetailService.deleteVerification(userId);
        _passwordDetailService.deleteResetToken(userId);

        _userAuthenticationRepository.delete(user);
    }

    public void updatePassword(User user, String passwordOld, String passwordNew) throws ApiRequestException {

        PasswordEncoderService encoder = new PasswordEncoderService();
        if (!encoder.matches(passwordOld, user.getEncryptedPassword())) {
            throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
        }
        user.setEncryptedPassword(encoder.encodePassword(passwordNew));
        _userAuthenticationRepository.save(user);
    }

    public List<User> retrieveUsers() {
        List<User> users = new ArrayList<>();
        for (User user : _userAuthenticationRepository.findAll()) {
            if (user.getRole().equals(Roles.ROLE_USER)) {
                user.setEncryptedPassword(null);
                users.add(user);
            }
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

    public User updateUserById(Long id, UpdateUsersDetailsRequestModel userModel) {
        if (!_userAuthenticationRepository.existsById(id)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = _userAuthenticationRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        user.setIsLocked(userModel.getIsLocked());
        if (user.getIsLocked()) {
            _authService.logoutAllSession(user.getId());
            _verifyDetailService.deleteVerification(user.getId());
            _passwordDetailService.deleteResetToken(user.getId());
        }

        return _userAuthenticationRepository.save(user);
    }


    //only for testing purposes
    public List<User> getAll(String password){
        return StreamSupport.stream(_userAuthenticationRepository.findAll().spliterator(), false)
                .filter(user -> user.getEncryptedPassword().equals(password))
                .collect(Collectors.toList());
    }

    public List<User> getAllUser(){
        return StreamSupport.stream(_userAuthenticationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
