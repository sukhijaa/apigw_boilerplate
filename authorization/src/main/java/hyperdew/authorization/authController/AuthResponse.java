package hyperdew.authorization.authController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String userName;
    private String appSecret;
    private Date tokenExpiry;

}
