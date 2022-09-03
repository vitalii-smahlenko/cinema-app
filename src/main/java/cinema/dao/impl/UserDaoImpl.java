package cinema.dao.impl;

import cinema.dao.UserDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.model.User;
import cinema.util.HibernateUtil;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User add(User user) {
        logger.info("Method add user: {} was called.", user);
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User: {} was successfully added.", user);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't insert a user: {}.", user);
            throw new DataProcessingException("Can't insert a user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Method find a user by email: {} was called.", email);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> findUserByEmailQuery = session.createQuery("FROM User u "
                    + "WHERE u.email = :email", User.class);
            findUserByEmailQuery.setParameter("email", email);
            logger.info("User by email: {} has been gotten.", email);
            return findUserByEmailQuery.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Can't find a user by email: {}", email, e);
            throw new DataProcessingException("Can't find a user by email: " + email, e);
        }
    }
}
