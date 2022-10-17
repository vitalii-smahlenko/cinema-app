package cinema.controller.lib;

import cinema.lib.EmailValidator;
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
        context = Mockito.mock(ConstraintValidatorContext.class);
        emailValidator = new EmailValidator();
    }

    @Test
    void isValid_ok() {
        boolean expected = true;

        boolean actual = emailValidator.isValid(VALID_EMAIL, context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutDot_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@emailcom", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalidemail.com", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithDotAtTheBegin_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid(".invalid@email.com", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithDotAtTheEnd_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@email.com.", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartToAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("@email.com", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartAfterAt_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@.com", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailWithoutPartAfterDot_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid("invalid@email.", context);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isValid_emailNull_notOk() {
        boolean expected = false;

        boolean actual = emailValidator.isValid(null, context);

        Assertions.assertEquals(expected,actual);
    }
}
