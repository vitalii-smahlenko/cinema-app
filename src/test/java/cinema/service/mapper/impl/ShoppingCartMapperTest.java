package cinema.service.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dto.response.ShoppingCartResponseDto;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingCartMapperTest {
    private static final Long ID = 1L;
    private static final int INDEX = 0;
    private ShoppingCartMapper shoppingCartMapper;
    private ShoppingCart shoppingCart;
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        shoppingCartMapper = new ShoppingCartMapper();

        User user = new User();
        user.setId(ID);
        user.setEmail("email@email.com");
        user.setPassword("12345678");

        Ticket ticket = new Ticket();
        ticket.setId(ID);
        ticket.setUser(user);

        tickets = new ArrayList<>();
        tickets.add(ticket);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(ID);
        shoppingCart.setUser(user);
        shoppingCart.setTickets(tickets);
    }

    @Test
    void mapToDto_ok() {
        List<Long> ticketsId = new ArrayList<>();
        ticketsId.add(tickets.get(INDEX).getId());
        ShoppingCartResponseDto expected = new ShoppingCartResponseDto();
        expected.setUserId(ID);
        expected.setTicketIds(ticketsId);

        ShoppingCartResponseDto actual = shoppingCartMapper.mapToDto(shoppingCart);

        assertEquals(expected, actual);
    }
}
