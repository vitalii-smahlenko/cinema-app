package cinema.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class MovieSessionRequestDto {
    @Positive
    private Long movieId;
    @Positive
    private Long cinemaHallId;
    @NotNull
    private LocalDateTime showTime;

    public Long getMovieId() {
        return movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    @Override
    public String toString() {
        return "MovieSessionRequestDto{"
                + "movieId: " + movieId
                + ", cinemaHallId:" + cinemaHallId
                + ", showTime: " + showTime + '}';
    }
}
