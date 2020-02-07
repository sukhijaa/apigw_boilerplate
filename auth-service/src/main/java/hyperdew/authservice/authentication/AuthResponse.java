package hyperdew.authservice.authentication;

import hyperdew.authservice.securityConfig.JWTTokenUtil;
import hyperdew.authservice.utils.SpringContextProvider;
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

    public AuthResponse(String accessToken) {
        JWTTokenUtil jwtTokenUtil = SpringContextProvider.getBean(JWTTokenUtil.class);

        this.accessToken = accessToken;
        this.refreshToken = accessToken;
        this.tokenExpiry = jwtTokenUtil.getExpirationDateFromToken(accessToken);
        this.userName = jwtTokenUtil.getUsernameFromToken(accessToken);
        this.appSecret = "";
    }

}
