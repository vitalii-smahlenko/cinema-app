package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.CinemaHallDao;
import cinema.dao.MovieDao;
import cinema.dao.MovieSessionDao;
import cinema.dao.RoleDao;
import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.dao.UserDao;
import cinema.exception.DataProcessingException;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingCartDaoImplTest extends AbstractTest {
    private static final LocalDateTime SHOW_TIME
            = LocalDateTime.of(2022, Month.OCTOBER, 17,
            13, 30, 15);
    private ShoppingCartDao shoppingCartDao;
    private ShoppingCart expected;
    private User user;
    private Role role;
    private UserDao userDao;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{ShoppingCart.class, User.class, Role.class, MovieSession.class,
                Movie.class, CinemaHall.class, Ticket.class};
    }

    @BeforeEach
    void setUp() {
        shoppingCartDao = new ShoppingCartDaoImpl(getSessionFactory());

        role = new Role();
        role.setRoleName(RoleName.USER);
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        roleDao.add(role);

        user = new User();
        user.setEmail("user@email.com");
        user.setPassword("12345678");
        user.setRoles(Set.of(role));
        userDao = new UserDaoImpl(getSessionFactory());
        userDao.add(user);

        Movie movie = new Movie();
        movie.setTitle("The Shawshank Redemption");
        movie.setDescription("American drama film written and directed by Frank Darabont");
        MovieDao movieDao = new MovieDaoImpl(getSessionFactory());
        movieDao.add(movie);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(20);
        cinemaHall.setDescription("Red hall");
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        cinemaHallDao.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(SHOW_TIME);
        MovieSessionDao movieSessionDao = new MovieSessionDaoImpl(getSessionFactory());
        movieSessionDao.add(movieSession);

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);
        TicketDao ticketDao = new TicketDaoImpl(getSessionFactory());
        ticketDao.add(ticket);

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        expected = new ShoppingCart();
        expected.setUser(user);
        expected.setTickets(tickets);
    }

    @Test
    void add_ok() {
        ShoppingCart actual = shoppingCartDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void getByUser_ok() {
        shoppingCartDao.add(expected);

        ShoppingCart actual = shoppingCartDao.getByUser(user);

        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getTickets(), actual.getTickets());
        assertIterableEquals(expected.getTickets(), actual.getTickets());
//        assertArrayEquals(expected.getTickets().toArray(), actual.getTickets().toArray());


//        assertEquals(expected, actual);
    }

    @Test
    void getByUser_userNull_notOk() {
        assertThrows(DataProcessingException.class,
                () -> shoppingCartDao.getByUser(null));
    }

    @Test
    void getByUser_userNotExistInDb_notOk() {
        assertThrows(DataProcessingException.class,
                () -> shoppingCartDao.getByUser(user));
    }

    @Test
    void update_ok() {
        shoppingCartDao.add(expected);

        User updatedUser = new User();
        updatedUser.setEmail("updatedUser@email.com");
        updatedUser.setPassword("12345678");
        updatedUser.setRoles(Set.of(role));
        userDao.add(updatedUser);
        expected.setUser(updatedUser);

        ShoppingCart actual = shoppingCartDao.update(expected);

        assertEquals(expected, actual);
    }

    @Test
    void update_shoppingCartNull_notOk() {
        assertThrows(DataProcessingException.class,
                () -> shoppingCartDao.update(null));
    }

    @Test
    void update_shoppingCartNotExistInDb_notOk() {
        assertThrows(DataProcessingException.class,
                () -> shoppingCartDao.update(expected));
    }
}
