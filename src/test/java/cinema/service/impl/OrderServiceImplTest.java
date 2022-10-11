package cinema.service.impl;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import cinema.dao.OrderDao;
import cinema.model.Order;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.OrderService;
import cinema.service.ShoppingCartService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "12345678";
    private List<Ticket> tickets;
    private OrderDao orderDao;
    private ShoppingCartService shoppingCartService;
    private OrderService orderService;
    private Order expected;
    private User user;

    @BeforeEach
    void setUp() {
        orderDao = mock(OrderDao.class);
        shoppingCartService = mock(ShoppingCartService.class);
        orderService = new OrderServiceImpl(orderDao, shoppingCartService);

        user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        tickets = new ArrayList<>();
        tickets.add(new Ticket());

        expected = new Order();
        expected.setId(ID);
        expected.setTickets(tickets);
        expected.setUser(user);
        expected.setOrderTime(LocalDateTime.now());
    }

    // I rewrote the method a bit.
    @Test
    void completeOrder_ok() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ID);
        shoppingCart.setTickets(tickets);
        shoppingCart.setUser(user);

        when(orderDao.add(any())).thenReturn(expected);
        doNothing().when(shoppingCartService).clear(shoppingCart);

        Order actual = orderService.completeOrder(shoppingCart);

        assertEquals(expected, actual);
    }

    @Test
    void getOrdersHistory_ok() {
        when(orderDao.getOrdersHistory(user)).thenReturn(List.of(expected));

        List<Order> actual = orderService.getOrdersHistory(user);

        assertEquals(List.of(expected), actual);
    }
}
