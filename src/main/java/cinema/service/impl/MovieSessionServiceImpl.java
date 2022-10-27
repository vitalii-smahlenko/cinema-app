package cinema.service.impl;

import cinema.dao.MovieSessionDao;
import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    private static final Logger LOGGER = LogManager.getLogger(MovieSessionServiceImpl.class);
    private final MovieSessionDao movieSessionDao;

    public MovieSessionServiceImpl(MovieSessionDao movieSessionDao) {
        this.movieSessionDao = movieSessionDao;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LOGGER.info("Found all available movie session with ID: {} and show date {}",
                movieId, date.toString());
        return movieSessionDao.findAvailableSessions(movieId, date);
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        LOGGER.info("Added {}", movieSession.toString());
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        LOGGER.info("Method get was called with ID: {}", id);
        MovieSession movieSession = movieSessionDao.get(id).orElseThrow(
                () -> new RuntimeException("Session with id " + id + " not found"));
        LOGGER.info("Found movie session by ID: {}.", id);
        return movieSession;
    }

    @Override
    public MovieSession update(MovieSession movieSession) {
        MovieSession updatedMovieSession = movieSessionDao.update(movieSession);
        LOGGER.info("Updated {}. New value {}.", movieSession, updatedMovieSession);
        return updatedMovieSession;
    }

    @Override
    public void delete(Long id) {
        movieSessionDao.delete(id);
        LOGGER.info("MovieSession by ID: {} was deleted.", id);
    }
}
