package cinema.security;

import cinema.model.User;
import cinema.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userService.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found by email: " + userEmail));
        UserBuilder builder
                = org.springframework.security.core.userdetails.User.withUsername(userEmail);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles().stream()
                .map(x -> x.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
