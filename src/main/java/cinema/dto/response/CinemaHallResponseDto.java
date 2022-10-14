package cinema.dto.response;

import java.util.Objects;

public class CinemaHallResponseDto {
    private Long id;
    private int capacity;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CinemaHallResponseDto)) {
            return false;
        }
        CinemaHallResponseDto other = (CinemaHallResponseDto) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.capacity, other.capacity)
                && Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + capacity;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
