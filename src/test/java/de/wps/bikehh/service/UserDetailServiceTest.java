package de.wps.bikehh.service;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
public class UserDetailServiceTest {

    @Autowired
    private UserDetailService userDetailService;


    @MockBean(UserAuthenticationRepository.class)
    private UserAuthenticationRepository userRepository;

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
    public void createUser() {
        List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123"), new User("another@mail.com", "anotherPassword123"), new User("test2@mail.com", "Password123"));
        //when(userRepository.save(Mockito.any(User.class))).then
        // then add to allUser list
        userDetailService.createUser("jasdkasd", "jaskdjaskl");
        //check if user is in the list

    }
}
