package cinema.dao.impl;

import cinema.dao.MovieSessionDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.model.MovieSession;
import cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = LogManager.getLogger(MovieSessionDaoImpl.class);
    private static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);

    @Override
    public MovieSession add(MovieSession movieSession) {
        logger.info("Method add movie session was called. CinemaHall = {}", movieSession);
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            logger.info("Movie session {} was successfully added.", movieSession);
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add cinema hall {}.", movieSession, e);
            throw new DataProcessingException("Can't insert a movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        logger.info("Method find available movie session by movie ID: {} "
                + "for date: {} was called.", movieId, date);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> findAvailableMovieSessionQuery =
                    criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> root = findAvailableMovieSessionQuery.from(MovieSession.class);
            Predicate moviePredicate = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate datePredicate = criteriaBuilder.between(root.get("showTime"),
                    date.atStartOfDay(), date.atTime(END_OF_DAY));
            Predicate allConditions = criteriaBuilder.and(moviePredicate, datePredicate);
            findAvailableMovieSessionQuery.select(root).where(allConditions);
            root.fetch("movie");
            root.fetch("cinemaHall");
            logger.info("A list of all movie sessions by movie ID: {} for date: {}"
                            + " has been received. {}", movieId, date,
                    session.createQuery(findAvailableMovieSessionQuery).getResultList());
            return session.createQuery(findAvailableMovieSessionQuery).getResultList();
        } catch (Exception e) {
            logger.error("Can't get available sessions for movie with ID: {} for date: {}.",
                    movieId, date);
            throw new DataProcessingException("Can't get available sessions for movie with ID: "
                    + movieId + " for date: " + date, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        logger.info("Method get movie session by id was called. ID = {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession ms "
                    + "JOIN FETCH ms.movie m "
                    + "JOIN FETCH ms.cinemaHall ch "
                    + "WHERE ms.id = :id", MovieSession.class);
            query.setParameter("id", id);
            logger.info("Movie session by ID: {} has been gotten.", id);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Can't get a movie session by ID: {}.", id, e);
            throw new DataProcessingException("Can't get a movie session by ID: " + id, e);
        }
    }
}
