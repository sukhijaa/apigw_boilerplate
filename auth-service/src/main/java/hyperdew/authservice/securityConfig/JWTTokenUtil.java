package hyperdew.authservice.securityConfig;


import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.userRegistry.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JWTTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;
    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve id from jwt token
    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String generateToken(UserModel userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUserName(), userDetails.getId());
    }

    public String generateTokenForApp(ApplicationModel appDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, appDetails.getApplicationName(), appDetails.getId());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject, String id) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public Boolean validateToken(String token, UserModel userDetails) {
        String username = getUsernameFromToken(token);
        String userId = getIdFromToken(token);

        return username.equals(userDetails.getUserName()) && userId.equals(userDetails.getId()) && !isTokenExpired(token);
    }

    public Boolean validateToken(String token, ApplicationModel applicationModel) {
        String appName = getUsernameFromToken(token);
        String appId = getIdFromToken(token);

        return appName.equals(applicationModel.getApplicationName()) && appId.equals(applicationModel.getId()) && !isTokenExpired(token);
    }
}
