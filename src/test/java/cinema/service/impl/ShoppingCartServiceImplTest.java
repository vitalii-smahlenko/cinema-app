package cinema.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.model.MovieSession;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.ShoppingCartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ShoppingCartServiceImplTest {
    private static final Long ID = 1L;
    private ShoppingCartService shoppingCartService;
    private ShoppingCartDao shoppingCartDao;
    private User user;
    private ShoppingCart expected;


    @BeforeEach
    void setUp() {
        shoppingCartDao = Mockito.mock(ShoppingCartDao.class);
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDao, ticketDao);

        Role role = new Role();
        role.setRoleName(RoleName.USER);
        user = new User();
        user.setId(ID);
        user.setEmail("email@email.com");
        user.setPassword("12345678");
        user.setRoles(Set.of(role));

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        expected = new ShoppingCart();
        expected.setId(ID);
        expected.setTickets(tickets);
        expected.setUser(user);
    }

    @Test
    void addSession_ok() {
        MovieSession movieSession = new MovieSession();
        when(shoppingCartDao.getByUser(any())).thenReturn(expected);

        shoppingCartService.addSession(movieSession, user);

        Mockito.verify(shoppingCartDao, times(1)).update(any());
    }

    @Test
    void getByUser_ok() {
        when(shoppingCartDao.getByUser(user)).thenReturn(expected);

        ShoppingCart actual = shoppingCartService.getByUser(user);

        assertEquals(expected, actual);
    }

    @Test
    void getByUser_userNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.getByUser(null));
    }

    @Test
    void getByUser_userNotExistInDb_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.getByUser(user));
    }

    @Test
    void registerNewShoppingCart_ok() {
        when(shoppingCartDao.add(any())).thenReturn(expected);

        shoppingCartService.registerNewShoppingCart(user);

        Mockito.verify(shoppingCartDao, times(1)).add(any());
    }

    @Test
    void registerNewShoppingCart_userNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.registerNewShoppingCart(null));
    }

    @Test
    void registerNewShoppingCart_userNotExistInDb_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.registerNewShoppingCart(user));
    }

    @Test
    void clear_ok() {
        when(shoppingCartDao.update(any())).thenReturn(expected);

        shoppingCartService.clear(expected);

        Mockito.verify(shoppingCartDao, times(1)).update(any());
    }

    @Test
    void clear_shoppingCartNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.clear(null));
    }

    @Test
    void clear_shoppingCartNotExisteInDb_notOk() {
        assertThrows(NullPointerException.class,
                () -> shoppingCartService.clear(expected));
    }
}
