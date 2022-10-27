package cinema.dto.response;

import java.util.Objects;

public class UserResponseDto {
    private Long id;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        UserResponseDto other = (UserResponseDto) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserResponseDto{"
                + "id=" + id
                + ", email='" + email + '\''
                + '}';
    }
}
