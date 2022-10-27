package cinema.dto.request;

import java.time.LocalDateTime;
import java.util.Objects;
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

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        MovieSessionRequestDto other = (MovieSessionRequestDto) o;

        return Objects.equals(this.movieId, other.movieId)
                && Objects.equals(this.cinemaHallId, other.cinemaHallId)
                && Objects.equals(this.showTime, other.showTime);
    }

    @Override
    public int hashCode() {
        int result = movieId != null ? movieId.hashCode() : 0;
        result = 31 * result + (cinemaHallId != null ? cinemaHallId.hashCode() : 0);
        result = 31 * result + (showTime != null ? showTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieSessionRequestDto{"
                + "movieId: " + movieId
                + ", cinemaHallId:" + cinemaHallId
                + ", showTime: " + showTime + '}';
    }
}
