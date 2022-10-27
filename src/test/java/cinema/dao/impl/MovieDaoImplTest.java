package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.MovieDao;
import cinema.exception.DataProcessingException;
import cinema.model.Movie;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final String TITLE = "The Shawshank Redemption";
    private static final String DESCRIPTION = "American drama film written and directed by "
            + "Frank Darabont";
    private MovieDao movieDao;
    private Movie expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Movie.class};
    }

    @BeforeEach
    void setUp() {
        movieDao = new MovieDaoImpl(getSessionFactory());

        expected = new Movie();
        expected.setId(ID);
        expected.setTitle(TITLE);
        expected.setDescription(DESCRIPTION);
    }

    @Test
    void add_ok() {
        Movie actual = movieDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        movieDao.add(expected);

        Movie actual = movieDao.get(ID).get();

        assertEquals(expected, actual);
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> movieDao.get(ID).get());
    }

    @Test
    void get_idNul_notOk() {
        assertThrows(DataProcessingException.class,
                () -> movieDao.get(null).get());
    }

    @Test
    void getAll_ok() {
        movieDao.add(expected);

        List<Movie> actual = movieDao.getAll();

        assertEquals(List.of(expected), actual);
    }
}
