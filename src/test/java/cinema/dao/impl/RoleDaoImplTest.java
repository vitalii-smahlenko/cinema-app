package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final RoleName ROLE_NAME = RoleName.USER;
    private RoleDao roleDao;
    private Role expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Role.class};
    }

    @BeforeEach
    void setUp() {
        roleDao = new RoleDaoImpl(getSessionFactory());
        expected = new Role();
        expected.setId(ID);
        expected.setRoleName(ROLE_NAME);
    }

    @Test
    void add_ok() {
        Role actual = roleDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void getByName_ok() {
        roleDao.add(expected);

        Role actual = roleDao.getByName("USER").get();

        assertEquals(expected, actual);
    }

    @Test
    void getByName_roleNotExistInDb_notOk() {
        assertThrows(DataProcessingException.class,
                () -> roleDao.getByName("USER").get());

    }

    @Test
    void getByName_roleNull_notOk() {
        assertThrows(DataProcessingException.class,
                () -> roleDao.getByName(null).get());

    }
}
