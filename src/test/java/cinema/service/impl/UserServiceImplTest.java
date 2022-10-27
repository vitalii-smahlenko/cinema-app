package cinema.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cinema.dao.UserDao;
import cinema.model.User;
import cinema.service.UserService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {
    private static final String EMAIL = "right@email.com";
    private static final String PASSWORD = "12345678";
    private static final Long USER_ID = 1L;
    private UserDao userDao;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private User expected;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(passwordEncoder, userDao);

        expected = new User();
        expected.setEmail(EMAIL);
        expected.setPassword(PASSWORD);
    }

    @Test
    void add_ok() {
        when(userDao.add(any())).thenReturn(expected);

        User actual = userService.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void findByEmail_ok() {
        when(userDao.findByEmail(any())).thenReturn(Optional.of(expected));

        User actual = userService.findByEmail(EMAIL).get();

        assertEquals(expected, actual);
    }

    @Test
    void findByEmail_emailNull_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> userService.findByEmail(null).get());
    }

    @Test
    void findByEmail_emailNotExistInDb_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> userService.findByEmail(EMAIL).get());
    }

    @Test
    void get_ok() {
        when(userDao.get(USER_ID)).thenReturn(Optional.of(expected));

        User actual = userService.get(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok_idNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> userService.get(null));
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(RuntimeException.class,
                () -> userService.get(USER_ID));
    }
}
