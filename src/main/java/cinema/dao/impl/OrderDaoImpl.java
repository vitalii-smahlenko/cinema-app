package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.OrderDao;
import cinema.exception.DataProcessingException;
import cinema.model.Order;
import cinema.model.User;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);

    public OrderDaoImpl(SessionFactory factory) {
        super(factory, Order.class);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        try (Session session = factory.openSession()) {
            Query<Order> getByUser = session.createQuery(
                    "SELECT DISTINCT o FROM Order o "
                            + "JOIN FETCH o.tickets t "
                            + "JOIN FETCH o.user u "
                            + "JOIN FETCH u.roles "
                            + "JOIN FETCH t.movieSession ms "
                            + "JOIN FETCH ms.cinemaHall "
                            + "JOIN FETCH ms.movie "
                            + "WHERE o.user = :user", Order.class);
            getByUser.setParameter("user", user);
            LOGGER.info("User {} get order history", user);
            return getByUser.getResultList();
        } catch (Exception e) {
            LOGGER.error("Not found order history for user {}.", user, e);
            throw new DataProcessingException("Not found order history for user " + user, e);
        }
    }
}
