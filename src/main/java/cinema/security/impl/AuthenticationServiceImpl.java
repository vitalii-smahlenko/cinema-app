package cinema.security.impl;

import cinema.exception.AuthenticationException;
import cinema.exception.RegistrationException;
import cinema.lib.Inject;
import cinema.lib.Service;
import cinema.model.User;
import cinema.security.AuthenticationService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import cinema.util.HashUtil;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
    @Inject
    private UserService userService;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        logger.info("Method login was called. Email = {}", email);
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            logger.info("Logged in. Email = {}", email);
            return userFromDb.get();
        }
        logger.error("Can't login. Email = {}", email);
        throw new AuthenticationException("Incorrect email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        logger.info("Method register was called. Email = {}", email);
        if (userService.findByEmail(email).isPresent()) {
            logger.error("Can't register. Email = {}.", email);
            throw new RegistrationException("User with this email is already registered!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        logger.info("User with email {} successfully registered.", email);
        return user;
    }

    private boolean matchPasswords(String rawPassword, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
