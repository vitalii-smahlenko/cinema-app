package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dao.AbstractTest;
import cinema.dao.TicketDao;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Role;
import cinema.model.Ticket;
import cinema.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private TicketDao ticketDao;
    private Ticket expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Ticket.class, MovieSession.class, User.class, CinemaHall.class,
                Movie.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        ticketDao = new TicketDaoImpl(getSessionFactory());

        expected = new Ticket();
        expected.setId(ID);
    }

    @Test
    void add_ok() {
        Ticket actual = ticketDao.add(expected);

        assertEquals(expected, actual);
    }
}
