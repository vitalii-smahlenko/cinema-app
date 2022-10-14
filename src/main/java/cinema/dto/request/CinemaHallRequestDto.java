package cinema.dto.request;

import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CinemaHallRequestDto {
    @Min(10)
    private int capacity;
    @Size(max = 200)
    private String description;

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
        if (!(o instanceof CinemaHallRequestDto)) {
            return false;
        }
        CinemaHallRequestDto other = (CinemaHallRequestDto) o;
        return Objects.equals(this.capacity, other.capacity)
                && Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
