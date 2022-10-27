package cinema.service.impl;

import cinema.model.Role.RoleName;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.RoleService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "12345678";
    private static final RoleName ROLE_NAME = RoleName.USER;
    private AuthenticationService authenticationService;
    private UserService userService;
    private ShoppingCartService shoppingCartService;
    private RoleService roleService;
    private User expected;
    private Role role;

    @BeforeEach
    void setUp() {
        userService = mock(UserServiceImpl.class);
        shoppingCartService = mock(ShoppingCartServiceImpl.class);
        roleService = mock(RoleServiceImpl.class);
        authenticationService
                = new AuthenticationServiceImpl(userService, shoppingCartService, roleService);

        role = new Role();
        role.setId(ID);
        role.setRoleName(ROLE_NAME);

        expected = new User();
        expected.setId(ID);
        expected.setEmail(EMAIL);
        expected.setPassword(PASSWORD);
        expected.setRoles(Set.of(role));
    }

    @Test
    void register_ok() {
        when(roleService.getByName("User")).thenReturn(role);
        when(userService.add(any())).thenReturn(expected);
        doNothing().when(shoppingCartService).registerNewShoppingCart(any());

        User actual = authenticationService.register(EMAIL, PASSWORD);

        assertEquals(expected, actual);
    }
}