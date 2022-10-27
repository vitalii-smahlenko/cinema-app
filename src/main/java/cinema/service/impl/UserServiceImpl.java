package cinema.service.impl;

import cinema.dao.UserDao;
import cinema.model.User;
import cinema.service.UserService;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final PasswordEncoder encoder;
    private final UserDao userDao;

    public UserServiceImpl(PasswordEncoder encoder, UserDao userDao) {
        this.encoder = encoder;
        this.userDao = userDao;
    }

    @Override
    public User add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = userDao.add(user);
        LOGGER.info("Add {}", newUser.toString());
        return newUser;
    }

    @Override
    public User get(Long id) {
        LOGGER.info("Method get was called with ID: {}", id);
        User user = userDao.get(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found"));
        LOGGER.info("Found {} by ID: {}", user, id);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> userByEmail = userDao.findByEmail(email);
        LOGGER.info("Found {} by emil {}", userByEmail.get(), email);
        return userByEmail;
    }
}
