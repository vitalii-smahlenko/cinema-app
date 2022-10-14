package cinema.service.impl;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import cinema.dao.CinemaHallDao;
import cinema.model.CinemaHall;
import cinema.service.CinemaHallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

class CinemaHallServiceImplTest {
    private static final Long ID = 1L;
    private CinemaHallService cinemaHallService;
    private CinemaHallDao cinemaHallDao;
    private CinemaHall expected;

    @BeforeEach
    void setUp() {
        cinemaHallDao = mock(CinemaHallDao.class);
        cinemaHallService = new CinemaHallServiceImpl(cinemaHallDao);

        expected = new CinemaHall();
        expected.setId(ID);
        expected.setCapacity(50);
        expected.setDescription("Red hall");
    }

    @Test
    void add_ok() {
        when(cinemaHallDao.add(expected)).thenReturn(expected);

        CinemaHall actual = cinemaHallService.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        when(cinemaHallDao.get(ID)).thenReturn(Optional.ofNullable(expected));

        CinemaHall actual = cinemaHallService.get(ID);

        assertEquals(expected, actual);
    }

    @Test
    void get_idNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> cinemaHallService.get(null));
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(RuntimeException.class,
                () -> cinemaHallService.get(ID));
    }

    @Test
    void getAll_ok() {
        when(cinemaHallDao.getAll()).thenReturn(List.of(expected));

        List<CinemaHall> actual = cinemaHallDao.getAll();

        assertEquals(List.of(expected), actual);
    }
}