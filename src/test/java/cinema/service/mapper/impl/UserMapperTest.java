package cinema.service.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cinema.dto.response.UserResponseDto;
import cinema.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMapperTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "email@email.com";
    private UserMapper userMapper;
    private User user;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();

        user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
    }

    @Test
    void mapToDto_ok() {
        UserResponseDto expected = new UserResponseDto();
        expected.setId(ID);
        expected.setEmail(EMAIL);

        UserResponseDto actual = userMapper.mapToDto(user);

        assertEquals(expected, actual);
    }
}
