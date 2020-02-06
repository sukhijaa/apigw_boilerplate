package hyperdew.authservice.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String userName;
    private String appSecret;
    private Date tokenExpiry;

}
