package cinema.dao.impl;

import cinema.dao.OrderDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.model.Order;
import cinema.model.User;
import cinema.util.HibernateUtil;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        logger.info("Method add order was called. Order = {}", order);
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("Order: {} was successfully added.", order);
            return order;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add order: {}.", order, e);
            throw new DataProcessingException("Can't add order " + order + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        logger.info("Method get list of orders by user: {} was called.", user);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> getOrdersHistoryQuery = session.createQuery("FROM Order o "
                    + "LEFT JOIN FETCH o.tickets t "
                    + "LEFT JOIN FETCH t.movieSession ms "
                    + "LEFT JOIN FETCH ms.movie "
                    + "LEFT JOIN FETCH ms.cinemaHall "
                    + "LEFT JOIN FETCH o.user "
                    + "WHERE o.user = :user", Order.class);
            getOrdersHistoryQuery.setParameter("user", user);
            logger.info("A list of orders for user: {} has been received.", user);
            return getOrdersHistoryQuery.getResultList();
        } catch (Exception e) {
            logger.error("Can't get orders history for user: {}.", user);
            throw new DataProcessingException("Can't get orders history for user: " + user, e);
        }
    }
}
