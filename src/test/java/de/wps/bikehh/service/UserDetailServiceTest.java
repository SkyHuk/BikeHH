package de.wps.bikehh.service;

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
public class UserDetailServiceTest {

    //@Autowired
    @Mock
    private AuthService authService;
    @Mock
    private VerifyDetailService verifyDetailService;
    @Mock
    private PasswordDetailService passwordDetailService;


    @Mock
    private UserAuthenticationRepository userRepository;
    @InjectMocks
    private UserDetailService userDetailService;


    /*@Before
    public void init(){
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));

        //UserAuthenticationRepository userRepository = mock(UserAuthenticationRepository.class);
        when(userRepository.findAll()).thenReturn(allUser);
    }*/

    //only for testing purposes
    @Test
    public void getUser() {
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));
        when(userRepository.findAll()).thenReturn(allUser);

        List<User> result = userDetailService.getAll("Password123");
        System.out.println(result.size());
    }

    @Test
    public void getAllUser() {
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));
        when(userRepository.findAll()).thenReturn(allUser);

        List<User> result = userDetailService.getAllUser();
        System.out.println(result.size());
        System.out.println(result.get(0).getEncryptedPassword());
    }

    //------------------

    @Test
    public void testCreateUser() {
        when(userRepository.existsByEmailAddress("test@web.de")).thenReturn(true);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        userDetailService.createUser("test@web.de", "Test1234");
        Mockito.verify(userRepository).existsByEmailAddress(anyString());
        Mockito.verify(userRepository).save(Mockito.any(User.class));




        //List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));
        //when(userRepository.save(Mockito.any(User.class))).then
        // then add to allUser list
        //userDetailService.createUser("jasdkasd", "jaskdjaskl");
        //check if user is in the list




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
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));
        when(userRepository.findAll()).thenReturn(allUser);

        List<User> result = userDetailService.retrieveUsers();
        assertEquals(3, result.size());
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
