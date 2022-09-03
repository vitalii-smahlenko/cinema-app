package cinema.util;

import cinema.dao.impl.CinemaHallDaoImpl;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashUtil {
    private static final Logger logger = LogManager.getLogger(CinemaHallDaoImpl.class);
    private static final String HASH_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        logger.info("Method hash password was called.");
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte element : digest) {
                hashedPassword.append(String.format("%02x", element));
            }
            logger.info("Password successfully hashed.");
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Can`t hash password");
            throw new RuntimeException("Can`t hash password!", e);
        }
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
