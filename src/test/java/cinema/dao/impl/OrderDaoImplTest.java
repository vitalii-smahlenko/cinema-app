package cinema.dao.impl;

import cinema.dao.AbstractTest;
import cinema.dao.OrderDao;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Order;
import cinema.model.Role;
import cinema.model.Ticket;
import cinema.model.User;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final LocalDateTime ORDER_TIME
            = LocalDateTime.of(2022, Month.OCTOBER, 14,00,00,00);
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

        user = new User();
        user.setId(ID);
        user.setEmail("email@email.com");
        user.setPassword("12345678");

        Ticket ticket = new Ticket();
        ticket.setId(ID);
        ticket.setUser(user);

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        expected = new Order();
        expected.setId(ID);
        expected.setUser(user);
        expected.setTickets(tickets);
        expected.setOrderTime(ORDER_TIME);
    }

    @Test
    void getOrdersHistory() {
        orderDao.add(expected);

        List<Order> actual = orderDao.getOrdersHistory(user);

        assertEquals(List.of(expected),actual);
    }
}