package dungcts.backendapi.com.shoplaptop.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequest() {
    }

    public LoginRequest(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

}