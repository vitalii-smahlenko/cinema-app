package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.RoleDao;
import cinema.dao.UserDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.model.User;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoImplTest extends AbstractTest {
    private static final String EMAIL = "user@email.com";
    private static final String PASSWORD = "12345678";
    private UserDao userDao;
    private User expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{User.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(getSessionFactory());

        Role role = new Role();
        role.setRoleName(RoleName.USER);
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        roleDao.add(role);

        expected = new User();
        expected.setEmail(EMAIL);
        expected.setPassword(PASSWORD);
        expected.setRoles(Set.of(role));
    }

    @Test
    void add_ok() {
        User actual = userDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        userDao.add(expected);

        User actual = userDao.get(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    void get_userWithSuchIdNotEexistInDb_ok() {
        assertThrows(DataProcessingException.class,
                () ->  userDao.get(expected.getId()).get());
    }

    @Test
    void get_idNull_ok() {
        assertThrows(DataProcessingException.class,
                () ->  userDao.get(expected.getId()).get());
    }

    @Test
    void findByEmail_ok() {
        userDao.add(expected);

        User actual = userDao.findByEmail(EMAIL).get();

        assertEquals(expected, actual);
    }

    @Test
    void findByEmail_userWithSuchEmailNotEexistInDb_notOk() {
        assertThrows(DataProcessingException.class,
                () -> userDao.findByEmail(EMAIL).get());
    }

    @Test
    void findByEmail_emailNull_notOk() {
        assertThrows(DataProcessingException.class,
                () -> userDao.findByEmail(null).get());
    }
}
