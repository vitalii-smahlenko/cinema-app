package cinema.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import cinema.dao.RoleDao;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.service.RoleService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoleServiceImplTest {
    private static final String ROLE_MANE = "USER";
    private RoleService roleService;
    private RoleDao roleDao;
    private Role expected;

    @BeforeEach
    void setUp() {
        roleDao = Mockito.mock(RoleDao.class);
        roleService = new RoleServiceImpl(roleDao);

        expected = new Role();
        expected.setRoleName(RoleName.USER);
    }

    @Test
    void add_ok() {
        Mockito.when(roleDao.add(any())).thenReturn(expected);

        Role actual = roleService.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void getByName_ok() {
        Mockito.when(roleDao.getByName(ROLE_MANE)).thenReturn(Optional.ofNullable(expected));

        Role actual = roleService.getByName(ROLE_MANE);

        assertEquals(expected, actual);
    }

    @Test
    void getByName_roleNameNull_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> roleService.getByName(null));
    }

    @Test
    void getByName_roleNotExistInDb_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> roleService.getByName(ROLE_MANE));
    }
}
