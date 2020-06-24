package de.wps.bikehh.benutzerverwaltung;

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceTest {

    //@Autowired
    @Mock
    AuthService authService;
    @Mock
    VerifyDetailService verifyDetailService;
    @Mock
    PasswordDetailService passwordDetailService;


    @Mock
    private UserAuthenticationRepository userRepository;
    @InjectMocks
    private UserDetailService userDetailService;



    //------------------

    @Test
    public void testCreateUser() {
        userDetailService.createUser("test@web.de", "Test1234");
        Mockito.verify(userRepository).existsByEmailAddress(anyString());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testUpdateUser() {

        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        User user = new User();
        UpdateUserDetailsRequestModel userUpdate = new UpdateUserDetailsRequestModel();
        userUpdate.setEmail("new@test.de");
        userUpdate.setPrivacySetting(2);
        userDetailService.updateUser(user, userUpdate);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }


    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        userDetailService.deleteUser(user);

        Mockito.verify(userRepository).delete(Mockito.any(User.class));
        Mockito.verify(authService).logoutAllSession(anyLong());
        Mockito.verify(verifyDetailService).deleteVerification(anyLong());
        Mockito.verify(passwordDetailService).deleteResetToken(anyLong());
    }

    @Test
    public void testUpdatePassword() {

        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        User user = new User();
        PasswordEncoderService encoder = new PasswordEncoderService();
        user.setEncryptedPassword(encoder.encode("Test1234"));
        userDetailService.updatePassword(user, "Test1234", "Kern1234");
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testRetrieveUsers() {
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123", Roles.ROLE_USER), new User("another@mail.com", "anotherPassword123", Roles.ROLE_USER), new User("test2@mail.com", "Password123", Roles.ROLE_ADMIN));
        when(userRepository.findAll()).thenReturn(allUser);
        Mockito.verify(userRepository).findAll();

        List<User> result = userDetailService.retrieveUsers();
        assertEquals(2, result.size());
    }

/*
    @Test
    public void testGetUserById() {

    }

    @Test
    public void testDeleteUserById() {
        when(userRepository.delete(Mockito.any(User.class));).then;  ??????????
        User user = new User();
        userDetailService.deleteUserById(user.getId());
        Mockito.verify(userRepository).delete(Mockito.any(User.class));
    }

    @Test
    public void testUpdateUserById() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        User user = new User();
        UpdateUsersDetailsRequestModel userUpdate = null;
        userDetailService.updateUserById(user.getId(), userUpdate);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testGetCurrentUser() {

    }*/

}
