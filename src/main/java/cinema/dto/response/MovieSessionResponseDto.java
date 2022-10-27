package cinema.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;

public class MovieSessionResponseDto {
    private Long movieSessionId;
    private Long movieId;
    private String movieTitle;
    private Long cinemaHallId;
    private LocalDateTime showTime;

    public Long getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(Long movieSessionId) {
        this.movieSessionId = movieSessionId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
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
        MovieSessionResponseDto other = (MovieSessionResponseDto) o;
        return Objects.equals(this.movieId, other.movieId);
    }

    @Override
    public int hashCode() {
        int result = movieSessionId != null ? movieSessionId.hashCode() : 0;
        result = 31 * result + (movieId != null ? movieId.hashCode() : 0);
        result = 31 * result + (movieTitle != null ? movieTitle.hashCode() : 0);
        result = 31 * result + (cinemaHallId != null ? cinemaHallId.hashCode() : 0);
        result = 31 * result + (showTime != null ? showTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieSessionResponseDto{"
                + "movieSessionId=" + movieSessionId
                + ", movieId=" + movieId
                + ", movieTitle='" + movieTitle + '\''
                + ", cinemaHallId=" + cinemaHallId
                + ", showTime=" + showTime
                + '}';
    }
}
