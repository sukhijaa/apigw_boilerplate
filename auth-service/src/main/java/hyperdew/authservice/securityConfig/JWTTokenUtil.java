package hyperdew.authservice.securityConfig;


import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.userRegistry.UserModel;
import hyperdew.authservice.utils.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;


@Component
public class JWTTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    private String getTokenIfContainsBearer(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return token;
    }

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
        token = getTokenIfContainsBearer(token);
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
        return getToken(userDetails.getId(), userDetails.getUserName(), null, userDetails.getUserRole());
    }

    public String generateToken(UserModel userModel, Date expiry) {
        return getToken(userModel.getId(), userModel.getUserName(), expiry, userModel.getUserRole());
    }

    public String generateToken(ApplicationModel appDetails) {
        return getToken(appDetails.getId(), appDetails.getApplicationName(), null, Roles.APP_ROLE);
    }

    public String generateToken(ApplicationModel appDetails, Date expiry) {
        return getToken(appDetails.getId(), appDetails.getApplicationName(), expiry, Roles.APP_ROLE);
    }


    public Boolean validateToken(String token, UserModel userDetails) {
        String username = getUsernameFromToken(token);
        String userId = getIdFromToken(token);

        return username.equals(userDetails.getUserName()) &&
                userId.equals(String.valueOf(userDetails.getId())) &&
                !isTokenExpired(token);
    }

    public Boolean validateToken(String token, ApplicationModel applicationModel) {
        String appName = getUsernameFromToken(token);
        String appId = getIdFromToken(token);

        return appName.equals(applicationModel.getApplicationName()) && appId.equals(applicationModel.getId()) && !isTokenExpired(token);
    }

    public String getToken(long id, String name, Date expiry, String role) {
        role = Optional.ofNullable(role).orElse(Roles.GUEST_ROLE);
        expiry = Optional.ofNullable(expiry).orElse(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));

        String jwtToken = Jwts.builder()
                .claim(Roles.TOKEN_ROLE_IDENTIFIER, role)
                .setId(String.valueOf(id))
                .setSubject(name)
                .setExpiration(expiry)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return "Bearer ".concat(jwtToken);
    }
}
