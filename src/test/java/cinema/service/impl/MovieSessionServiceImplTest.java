package cinema.service.impl;

import cinema.dao.MovieSessionDao;
import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieSessionServiceImplTest {
    private static final Long ID = 1L;
    private MovieSessionService movieSessionService;
    private MovieSessionDao movieSessionDao;
    private MovieSession expected;

    @BeforeEach
    void setUp() {
        movieSessionDao = mock(MovieSessionDao.class);
        movieSessionService = new MovieSessionServiceImpl(movieSessionDao);
        expected = new MovieSession();
        expected.setId(ID);
        expected.setShowTime(LocalDateTime.now());
    }

    @Test
    void findAvailableSessions() {
        when(movieSessionDao.findAvailableSessions(ID,LocalDate.now()))
                .thenReturn(List.of(expected));

        List<MovieSession> actual = movieSessionService.findAvailableSessions(ID, LocalDate.now());

        assertEquals(List.of(expected), actual);
    }

    @Test
    void add_ok() {
        when(movieSessionDao.add(expected)).thenReturn(expected);

        MovieSession actual = movieSessionService.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        when(movieSessionDao.get(ID)).thenReturn(Optional.ofNullable(expected));

        MovieSession actual = movieSessionService.get(ID);

        assertEquals(expected, actual);
    }

    @Test
    void get_idNotExistInDb_ontOk() {
        assertThrows(RuntimeException.class,
                () -> movieSessionService.get(ID));
    }

    @Test
    void get_idNull_ontOk() {
        assertThrows(RuntimeException.class,
                () -> movieSessionService.get(null));
    }

    @Test
    void update_ok() {
        when(movieSessionDao.update(expected)).thenReturn(expected);

        MovieSession actual = movieSessionService.update(expected);

        assertEquals(expected, actual);
    }

    @Test
    void delete_ok() {
        movieSessionService.delete(ID);

        verify(movieSessionDao,times(1)).delete(ID);
    }
}