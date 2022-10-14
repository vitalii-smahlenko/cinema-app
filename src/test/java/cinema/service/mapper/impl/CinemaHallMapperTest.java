package cinema.service.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dto.request.CinemaHallRequestDto;
import cinema.dto.response.CinemaHallResponseDto;
import cinema.model.CinemaHall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinemaHallMapperTest {
    private static final int CAPACITY = 50;
    private static final String DESCRIPTION = "Red hall";
    private CinemaHallMapper cinemaHallMapper;
    private CinemaHall cinemaHall;


    @BeforeEach
    void setUp() {
        cinemaHallMapper = new CinemaHallMapper();
        cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(CAPACITY);
        cinemaHall.setDescription(DESCRIPTION);
    }

    @Test
    void mapToModel_ok() {
        CinemaHall expected = cinemaHall;

        CinemaHallRequestDto cinemaHallRequestDto = new CinemaHallRequestDto();
        cinemaHallRequestDto.setCapacity(CAPACITY);
        cinemaHallRequestDto.setDescription(DESCRIPTION);
        CinemaHall actual = cinemaHallMapper.mapToModel(cinemaHallRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDto_ok() {
        CinemaHallResponseDto expected = new CinemaHallResponseDto();
        expected.setCapacity(CAPACITY);
        expected.setDescription(DESCRIPTION);

        CinemaHallResponseDto actual = cinemaHallMapper.mapToDto(cinemaHall);

        assertEquals(expected, actual);
    }
}
