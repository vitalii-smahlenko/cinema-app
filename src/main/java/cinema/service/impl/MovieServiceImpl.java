package cinema.service.impl;

import cinema.dao.MovieDao;
import cinema.model.Movie;
import cinema.service.MovieService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LogManager.getLogger(MovieServiceImpl.class);
    private final MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie add(Movie movie) {
        LOGGER.info("Added {}", movie);
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        LOGGER.info("Method get was called with ID: {}", id);
        Movie movie = movieDao.get(id).orElseThrow(
                () -> new RuntimeException("Can't get movie by id " + id));
        LOGGER.info("Found movie by ID: {}", id);
        return movie;
    }

    @Override
    public List<Movie> getAll() {
        LOGGER.info("Found all movies. {}", movieDao.getAll());
        return movieDao.getAll();
    }
}
