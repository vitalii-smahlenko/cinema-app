package cinema.service.impl;

import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.model.MovieSession;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.ShoppingCartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingCartServiceImpl.class);
    private final ShoppingCartDao shoppingCartDao;
    private final TicketDao ticketDao;

    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao, TicketDao ticketDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        ticketDao.add(ticket);
        shoppingCart.getTickets().add(ticket);
        ShoppingCart updatedShoppingCart = shoppingCartDao.update(shoppingCart);
        LOGGER.info("Added movieSession {} to shopping cart {} user {}.",
                movieSession.toString(), updatedShoppingCart.toString(), user.toString());
    }

    @Override
    public ShoppingCart getByUser(User user) {
        ShoppingCart shoppingCartByUser = shoppingCartDao.getByUser(user);
        LOGGER.info("Find {} by {}.", shoppingCartByUser.toString(), user.toString());
        return shoppingCartByUser;
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        ShoppingCart newShoppingCart = shoppingCartDao.add(shoppingCart);
        LOGGER.info("Added {} to {}", newShoppingCart.toString(), user.toString());
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setTickets(null);
        ShoppingCart updatedShoppingCart = shoppingCartDao.update(shoppingCart);
        LOGGER.info("Updated {}. New value {}.", shoppingCart.toString(),
                updatedShoppingCart.toString());
    }
}
