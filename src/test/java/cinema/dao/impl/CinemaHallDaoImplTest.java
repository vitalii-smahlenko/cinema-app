package cinema.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cinema.dao.AbstractTest;
import cinema.dao.CinemaHallDao;
import cinema.exception.DataProcessingException;
import cinema.model.CinemaHall;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinemaHallDaoImplTest extends AbstractTest {
    private static final Long ID = 1L;
    private static final int CAPACITY = 50;
    private static final String DESCRIPTION = "Rad hall";
    private CinemaHallDao cinemaHallDao;
    private CinemaHall expected;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{CinemaHall.class};
    }

    @BeforeEach
    void setUp() {
        cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());

        expected = new CinemaHall();
        expected.setId(ID);
        expected.setCapacity(CAPACITY);
        expected.setDescription(DESCRIPTION);
    }

    @Test
    void add_ok() {
        CinemaHall actual = cinemaHallDao.add(expected);

        assertEquals(expected, actual);
    }

    @Test
    void get_ok() {
        cinemaHallDao.add(expected);

        CinemaHall actual = cinemaHallDao.get(ID).get();

        assertEquals(expected, actual);
    }

    @Test
    void get_idNotExistInDb_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> cinemaHallDao.get(ID).get());
    }

    @Test
    void get_idNul_notOk() {
        assertThrows(DataProcessingException.class,
                () -> cinemaHallDao.get(null).get());
    }

    @Test
    void getAll_ok() {
        cinemaHallDao.add(expected);

        List<CinemaHall> actual = cinemaHallDao.getAll();

        assertEquals(List.of(expected), actual);
    }
}
