package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.UserDao;
import cinema.exception.DataProcessingException;
import cinema.model.User;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(SessionFactory factory) {
        super(factory, User.class);
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = factory.openSession()) {
            Query<User> getUserByIdQuery = session.createQuery(
                    "FROM User u "
                            + "JOIN FETCH u.roles "
                            + "WHERE u.id = :id", User.class);
            getUserByIdQuery.setParameter("id", id);
            LOGGER.info("Found user {} by ID: {}",
                    getUserByIdQuery.uniqueResultOptional().get(), id);
            return getUserByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("User with ID: " + id + " not found", e);
            throw new DataProcessingException("User with ID " + id + " not found", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = factory.openSession()) {
            Query<User> findByEmail = session.createQuery(
                    "FROM User u "
                            + "JOIN FETCH u.roles "
                            + "WHERE email = :email", User.class);
            findByEmail.setParameter("email", email);
            LOGGER.info("Found user {} by email {}",
                    findByEmail.uniqueResultOptional().get(), email);
            return findByEmail.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("User with email " + email + " not found", e);
            throw new DataProcessingException("User with email " + email + " not found", e);
        }
    }
}
