package cinema.dao.impl;

import cinema.dao.MovieDao;
import cinema.exception.DataProcessingException;
import cinema.lib.Dao;
import cinema.model.Movie;
import cinema.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieDaoImpl implements MovieDao {
    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    @Override
    public Movie add(Movie movie) {
        logger.info("Method add movie was called. CinemaHall = {}", movie);
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
            logger.info("{} was successfully added.", movie.getTitle());
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add movie {}.", movie, e);
            throw new DataProcessingException("Can't insert a movie: " + movie, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        logger.info("Method get movie by id was called. ID = {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Movie by ID: {} has been gotten.", id);
            return Optional.ofNullable(session.get(Movie.class, id));
        } catch (Exception e) {
            logger.error("Can't get a movie by ID: {}.", id, e);
            throw new DataProcessingException("Can't get a movie by ID: " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        logger.info("Method get all movie was called.");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<Movie> getAllMovieQuery = session.getCriteriaBuilder()
                    .createQuery(Movie.class);
            getAllMovieQuery.from(Movie.class);
            logger.info("A list of all movie has been received. {}",
                    session.createQuery(getAllMovieQuery).getResultList());
            return session.createQuery(getAllMovieQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movies", e);
        }
    }
}
