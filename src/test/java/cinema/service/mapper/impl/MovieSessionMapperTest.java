package cinema.service.mapper.impl;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.service.impl.CinemaHallServiceImpl;
import cinema.service.impl.MovieServiceImpl;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import cinema.model.MovieSession;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

class MovieSessionMapperTest {
    private static final LocalDateTime SHOW_TIME = LocalDateTime.now();
    private static final Long ID = 1L;
    private static final String MOVIE_TITLE = "The Shawshank Redemption";
    private MovieSessionMapper movieSessionMapper;
    private CinemaHallService cinemaHallService;
    private MovieService movieService;
    private MovieSession movieSession;
    private Movie movie;
    private CinemaHall cinemaHall;

    @BeforeEach
    void setUp() {
        cinemaHallService = mock(CinemaHallServiceImpl.class);
        movieService = mock(MovieServiceImpl.class);
        movieSessionMapper = new MovieSessionMapper(cinemaHallService, movieService);

        movie = new Movie();
        movie.setTitle(MOVIE_TITLE);
        movie.setDescription("American drama film written and directed by Frank Darabont");

        cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Read hall");

        movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(SHOW_TIME);
    }

    @Test
    void mapToModel() {
        MovieSession expected = movieSession;

        MovieSessionRequestDto movieSessionRequestDto = new MovieSessionRequestDto();
        movieSessionRequestDto.setShowTime(SHOW_TIME);
        movieSessionRequestDto.setMovieId(ID);
        movieSessionRequestDto.setCinemaHallId(ID);
        when(movieService.get(movieSessionRequestDto.getMovieId())).thenReturn(movie);
        when(cinemaHallService.get(movieSessionRequestDto.getCinemaHallId()))
                .thenReturn(cinemaHall);
        MovieSession actual = movieSessionMapper.mapToModel(movieSessionRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDto() {
        MovieSessionResponseDto expected = new MovieSessionResponseDto();
        expected.setMovieId(ID);
        expected.setMovieSessionId(ID);
        expected.setMovieTitle(MOVIE_TITLE);
        expected.setShowTime(SHOW_TIME);
        expected.setCinemaHallId(ID);

        movieSession.setId(ID);
        movie.setId(ID);
        cinemaHall.setId(ID);
        MovieSessionResponseDto actual = movieSessionMapper.mapToDto(movieSession);

        assertEquals(expected, actual);
    }
}
