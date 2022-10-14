package cinema.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderResponseDto {
    private Long id;
    private List<Long> ticketIds;
    private Long userId;
    private LocalDateTime orderTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderResponseDto)) {
            return false;
        }
        OrderResponseDto other = (OrderResponseDto) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.userId, other.userId)
                && Objects.equals(this.ticketIds, other.ticketIds)
                && Objects.equals(this.orderTime, other.orderTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ticketIds != null ? ticketIds.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderResponseDto{"
                + "id=" + id
                + ", ticketIds=" + ticketIds
                + ", userId=" + userId
                + ", orderTime=" + orderTime
                + '}';
    }
}
