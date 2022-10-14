package cinema.service.impl;

import cinema.model.Movie;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import cinema.dao.MovieDao;
import cinema.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {
    private static final Long ID = 1L;
    private MovieService movieService;
    private MovieDao movieDao;
    private Movie expected;

    @BeforeEach
    void setUp() {
        movieDao = mock(MovieDao.class);
        movieService = new MovieServiceImpl(movieDao);

        expected = new Movie();
        expected.setId(ID);
        expected.setTitle("The Shawshank Redemption");
        expected.setDescription("American drama film written and directed by Frank Darabont");
    }

    @Test
    void add_ok() {
        when(movieDao.add(expected)).thenReturn(expected);

        Movie actual = movieService.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        when(movieDao.get(ID)).thenReturn(Optional.ofNullable(expected));

        Movie actual = movieService.get(ID);

        assertEquals(expected, actual);
    }

    @Test
    void get_idNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> movieService.get(null));
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(RuntimeException.class,
                () -> movieService.get(ID));
    }

    @Test
    void getAll_ok() {
        when(movieDao.getAll()).thenReturn(List.of(expected));

        List<Movie> actual = movieService.getAll();

        assertEquals(List.of(expected), actual);
    }
}
