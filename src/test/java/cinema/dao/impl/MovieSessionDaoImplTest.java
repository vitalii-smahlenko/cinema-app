package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.MovieSessionDao;
import cinema.dao.CinemaHallDao;
import cinema.dao.MovieDao;
import cinema.exception.DataProcessingException;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieSessionDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final LocalDateTime SHOW_TIME
            = LocalDateTime.of(2022, Month.OCTOBER, 13,
            8, 30, 10);
    private MovieSessionDao movieSessionDao;
    private MovieSession expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{MovieSession.class, Movie.class, CinemaHall.class};
    }

    @BeforeEach
    void setUp() {
        movieSessionDao = new MovieSessionDaoImpl(getSessionFactory());

        expected = new MovieSession();
        expected.setShowTime(SHOW_TIME);
    }

    @Test
    void add_ok() {
        MovieSession actual = movieSessionDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        movieSessionDao.add(expected);

        MovieSession actual = movieSessionDao.get(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> movieSessionDao.get(ID).get());
    }

    @Test
    void get_idNul_notOk() {
        assertThrows(DataProcessingException.class,
                () -> movieSessionDao.get(null).get());
    }

    @Test
    void findAvailableSessions_ok() {
        Movie movie = new Movie();
        MovieDao movieDao = new MovieDaoImpl(getSessionFactory());
        movieDao.add(movie);

        CinemaHall cinemaHall = new CinemaHall();
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        cinemaHallDao.add(cinemaHall);

        expected.setMovie(movie);
        expected.setCinemaHall(cinemaHall);

        movieSessionDao.add(expected);

        List<MovieSession> actual
                = movieSessionDao.findAvailableSessions(movie.getId(), SHOW_TIME.toLocalDate());

        assertEquals(List.of(expected), actual);
    }

    @Test
    void findAvailableSessions_notOk() {
        Movie movie = new Movie();
        MovieDao movieDao = new MovieDaoImpl(getSessionFactory());
        movieDao.add(movie);

        CinemaHall cinemaHall = new CinemaHall();
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        cinemaHallDao.add(cinemaHall);

        expected.setMovie(movie);
        expected.setCinemaHall(cinemaHall);

        movieSessionDao.add(expected);

        List<MovieSession> actual
                = movieSessionDao.findAvailableSessions(movie.getId(), SHOW_TIME.toLocalDate());

        assertEquals(List.of(expected), actual);
    }
}
