package be.kdg.processor.user.service;

import be.kdg.processor.user.exceptions.UserNotFoundException;
import be.kdg.processor.user.model.User;
import be.kdg.processor.user.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    private User user;
    private User updateUser;

    private UserService userService;


    @Before
    public void setUp() {
        initMocks(this);

        userService = new UserService(mockUserRepository);

        user = User.builder()
                .id(1L)
                .userName("admin")
                .passWord("password")
                .build();

        updateUser = User.builder()
                .id(1L)
                .userName("superadmin")
                .passWord("1234")
                .build();

        when(mockUserRepository.save(any()))
                .thenReturn(user);

        when(mockUserRepository.updateUserById(any(), any(), any()))
                .thenReturn(1);

        when(mockUserRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(mockUserRepository.findAll())
                .thenReturn(List.of(user));


    }


    @Test
    @Transactional
    public void readUser() throws UserNotFoundException {
        final Long id = user.getId();
        final User result = userService.get(id);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void readAllUsers() {
        List<User> all = userService.getAll();

        assertNotNull(all);
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    public void createUser() {
        //wrong and false username...
        final String username = "admin";
        User result = userService.save(User.builder().build());

        assertThat(username.equalsIgnoreCase(result.getUserName()), is(true));
        assertThat("superadmin".equalsIgnoreCase(result.getUserName()), is(false));
    }

    @Test
    public void updateUser() throws UserNotFoundException {
        String newName = "superadmin";
        User update = userService.update(1L, updateUser);

        assertNotNull(update);
        assertThat(newName.equalsIgnoreCase(update.getUserName()), is(true));
        assertThat("admin".equalsIgnoreCase(update.getUserName()), is(false));
    }

    @Test
    public void removeUser() throws UserNotFoundException {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        User userTest = userService.remove(1L);
        Assert.assertNotNull(userTest);
    }
}