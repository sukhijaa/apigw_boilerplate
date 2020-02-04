package hyperdew.apigw.authorization;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.utilities.SpringContextProvider;

/**
 * All calls to Authorization MS should go from here
 */
public class AuthorizationService {

    public static AuthorizationService authorizationService;
    private AuthServiceClientKit authServiceClientKit;


    private AuthorizationService() {
        authServiceClientKit = SpringContextProvider.getBean(AuthServiceClientKit.class);
    }

    public static AuthorizationService getInstance() {
        if (authorizationService == null) {
            authorizationService = new AuthorizationService();
        }

        return authorizationService;
    }

    public AuthResponse getAuthTokenFromAuthorizationMS(RequestContext requestContext) {
        AuthResponse authResponse = authServiceClientKit.getAuthToken();
        return authResponse;
    }

}
