package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dao.AbstractTest;
import cinema.dao.CinemaHallDao;
import cinema.dao.MovieDao;
import cinema.dao.MovieSessionDao;
import cinema.dao.OrderDao;
import cinema.dao.RoleDao;
import cinema.dao.TicketDao;
import cinema.dao.UserDao;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Order;
import cinema.model.Role;
import cinema.model.Role.RoleName;
import cinema.model.Ticket;
import cinema.model.User;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final LocalDateTime ORDER_TIME
            = LocalDateTime.of(2022, Month.OCTOBER, 14,
            00, 00, 00);
    private OrderDao orderDao;
    private Order expected;
    private User user;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Order.class, Ticket.class, Movie.class,
                MovieSession.class, CinemaHall.class, User.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        orderDao = new OrderDaoImpl(getSessionFactory());

        Role role = new Role();
        role.setRoleName(RoleName.USER);
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        roleDao.add(role);

        user = new User();
        user.setEmail("user@email.com");
        user.setPassword("12345678");
        user.setRoles(Set.of(role));
        UserDao userDao = new UserDaoImpl(getSessionFactory());
        userDao.add(user);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Blue hall");
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        cinemaHallDao.add(cinemaHall);

        Movie movie = new Movie();
        movie.setTitle("The Shawshank Redemption");
        movie.setDescription("American drama film written and directed by Frank Darabont");
        MovieDao movieDao = new MovieDaoImpl(getSessionFactory());
        movieDao.add(movie);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        MovieSessionDao movieSessionDao = new MovieSessionDaoImpl(getSessionFactory());
        movieSessionDao.add(movieSession);

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);
        TicketDao ticketDao = new TicketDaoImpl(getSessionFactory());
        ticketDao.add(ticket);

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        expected = new Order();
        expected.setUser(user);
        expected.setTickets(tickets);
        expected.setOrderTime(ORDER_TIME);
    }

    @Test
    void add_ok() {
        Order actual = orderDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void getOrdersHistory_ok() {
        orderDao.add(expected);

        List<Order> actual = orderDao.getOrdersHistory(user);

        assertEquals(List.of(expected), actual);
    }
}
