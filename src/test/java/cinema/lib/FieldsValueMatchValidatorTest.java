package cinema.lib;

import cinema.dto.request.UserRequestDto;
import javax.validation.ConstraintValidatorContext;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class FieldsValueMatchValidatorTest {
    private FieldsValueMatchValidator fieldsValueMatchValidator;
    private ConstraintValidatorContext context;
    private UserRequestDto userRequestDto;
    private FieldsValueMatch fieldsValueMatch;

    @BeforeEach
    void setUp() {
        fieldsValueMatchValidator = new FieldsValueMatchValidator();
        context = mock(ConstraintValidatorContext.class);
        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("userRequestDto@email.com");
    }

    @Test
    void name() {

    }

    @Test
    void isValid() {
        boolean expected = true;
        userRequestDto.setPassword("123");
        userRequestDto.setRepeatPassword("123");


        fieldsValueMatchValidator.isValid(userRequestDto, context);

    }
}