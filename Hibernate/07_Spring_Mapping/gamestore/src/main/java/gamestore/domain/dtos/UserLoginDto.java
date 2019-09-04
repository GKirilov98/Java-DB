package gamestore.domain.dtos;

import gamestore.domain.Messages;

import javax.validation.constraints.NotNull;

public class UserLoginDto {
        private String email;
        private String password;

    public UserLoginDto(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    @NotNull(message = Messages.EMAIL_CANNOT_BE_NULL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = Messages.PASSWORD_CANNOT_BE_NULL)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
