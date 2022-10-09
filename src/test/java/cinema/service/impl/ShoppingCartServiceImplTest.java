package cinema.service.impl;

import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;

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

        expected = new ShoppingCart();
        expected.setId(ID);
        expected.setTickets(List.of(new Ticket()));
        expected.setUser(user);
    }

    @Test
    void addSession_ok() {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(new Movie());
        movieSession.setId(ID);
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setCinemaHall(new CinemaHall());

        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        Ticket ticket = new Ticket();
        ticket.setId(ID);
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);

        Mockito.when(shoppingCartDao.getByUser(any())).thenReturn(expected);
        //Mockito.when(ticketDao.add(any())).thenReturn(ticket);
        //Mockito.when(expected.getTickets().add(ticket)).thenReturn(true);

        shoppingCartService.addSession(movieSession, user);

        Mockito.verify(shoppingCartDao,times(1)).update(any());
    }

    @Test
    void getByUser_ok() {
        Mockito.when(shoppingCartDao.getByUser(user)).thenReturn(expected);

        ShoppingCart actual = shoppingCartService.getByUser(user);

        assertEquals(expected, actual);
    }


}
