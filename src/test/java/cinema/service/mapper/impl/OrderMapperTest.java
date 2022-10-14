package cinema.service.mapper.impl;

import cinema.dto.response.OrderResponseDto;
import cinema.model.Order;
import cinema.model.Ticket;
import cinema.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderMapperTest {
    private static final Long ID = 1L;
    private static final LocalDateTime ORDER_TIME = LocalDateTime.now();
    private static final int INDEX = 0;
    private OrderMapper orderMapper;
    private Order order;
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();

        User user = new User();
        user.setId(ID);
        user.setEmail("email@email.com");
        user.setPassword("12345678");

        Ticket ticket = new Ticket();
        ticket.setId(ID);
        ticket.setUser(user);

        tickets = new ArrayList<>();
        tickets.add(ticket);

        order = new Order();
        order.setId(ID);
        order.setUser(user);
        order.setTickets(tickets);
        order.setOrderTime(ORDER_TIME);
    }

    @Test
    void mapToDto() {
        List<Long> ticketsIds = new ArrayList<>();
        ticketsIds.add(tickets.get(INDEX).getId());
        OrderResponseDto expected = new OrderResponseDto();
        expected.setId(ID);
        expected.setUserId(ID);
        expected.setOrderTime(ORDER_TIME);
        expected.setTicketIds(ticketsIds);

        OrderResponseDto actual = orderMapper.mapToDto(order);

        assertEquals(expected, actual);
    }
}
