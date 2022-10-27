package cinema.dto.response;

import java.util.List;
import java.util.Objects;

public class ShoppingCartResponseDto {
    private Long userId;
    private List<Long> ticketIds;

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        ShoppingCartResponseDto other = (ShoppingCartResponseDto) o;
        return Objects.equals(this.userId, other.userId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (ticketIds != null ? ticketIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShoppingCartResponseDto{"
                + "userId=" + userId
                + ", ticketIds=" + ticketIds
                + '}';
    }
}
