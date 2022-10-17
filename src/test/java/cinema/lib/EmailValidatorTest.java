package cinema.lib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class EmailValidatorTest {
    private static final String VALID_EMAIL = "validEmail@email.com";
    private EmailValidator emailValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        context = mock(ConstraintValidatorContext.class);
        emailValidator = new EmailValidator();
    }

    @Test
    void isValid_ok() {
        boolean expected = true;

        boolean actual = emailValidator.isValid(VALID_EMAIL, context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutDot_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@emailcom", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalidemail.com", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithDotAtTheBegin_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid(".invalid@email.com", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithDotAtTheEnd_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@email.com.", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartToAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("@email.com", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartAfterAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@.com", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartAfterDot_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@email.", context);

        assertEquals(expected,actual);
    }

    @Test
    void isValid_emailNull_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid(null, context);

        assertEquals(expected,actual);
    }
}
