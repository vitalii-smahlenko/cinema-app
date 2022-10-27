package cinema.service.mapper.impl;

import cinema.dto.response.MovieResponseDto;
import cinema.model.CinemaHall;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dto.request.MovieRequestDto;
import cinema.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieMapperTest {
    private static final String TITLE = "The Shawshank Redemption";
    private static final String DESCRIPTION = "American drama film written and directed by "
            + "Frank Darabont";
    private MovieMapper movieMapper;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapper();
        movie = new Movie();
        movie.setTitle(TITLE);
        movie.setDescription(DESCRIPTION);
    }

    @Test
    void mapToModel_ok() {
        Movie expected = movie;

        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setTitle(TITLE);
        movieRequestDto.setDescription(DESCRIPTION);
        Movie actual = movieMapper.mapToModel(movieRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDto_ok() {
        MovieResponseDto expected = new MovieResponseDto();
        expected.setTitle(TITLE);
        expected.setDescription(DESCRIPTION);

        MovieResponseDto actual = movieMapper.mapToDto(movie);

        assertEquals(expected, actual);
    }
}
