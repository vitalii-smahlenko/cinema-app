package cinema.dao.impl;

import cinema.dao.CinemaHallDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.model.CinemaHall;
import cinema.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger logger = LogManager.getLogger(CinemaHallDaoImpl.class);

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        logger.info("Method add cinema hall was called. CinemaHall = {}", cinemaHall);
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
            logger.info("Cinema hall: {} was successfully added.", cinemaHall);
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add cinema hall {}.", cinemaHall, e);
            throw new DataProcessingException("Can't insert a cinema hall {} " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        logger.info("Method get cinema hall by id was called. Id = {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Cinema call by ID: {} has been gotten.", id);
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            logger.error("Can't get a cinema hall by ID: {}.", id, e);
            throw new DataProcessingException("Can't get a cinema hall by ID: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        logger.info("Method get all cinema hall was called.");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<CinemaHall> getAllCinemaHallQuery = session.getCriteriaBuilder()
                    .createQuery(CinemaHall.class);
            getAllCinemaHallQuery.from(CinemaHall.class);
            logger.info("A list of all movie halls has been received. {}",
                    session.createQuery(getAllCinemaHallQuery).getResultList());
            return session.createQuery(getAllCinemaHallQuery).getResultList();
        } catch (Exception e) {
            logger.error("Can't get a list of all movie halls.", e);
            throw new DataProcessingException("Can't get a list of all movie halls", e);
        }
    }
}
