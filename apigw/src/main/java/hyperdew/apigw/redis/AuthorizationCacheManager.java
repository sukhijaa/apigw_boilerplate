package hyperdew.apigw.redis;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.authorization.AuthResponse;
import hyperdew.apigw.authorization.AuthorizationService;
import hyperdew.apigw.utilities.RequestHeaders;
import hyperdew.apigw.utilities.SpringContextProvider;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class AuthorizationCacheManager {

    private static AuthorizationCacheManager authorizationCacheManager;
    private AuthorizationCacheUtil authorizationCacheUtil;
    private AuthorizationService authorizationService;

    private AuthorizationCacheManager() {
        authorizationCacheUtil = SpringContextProvider.getBean(AuthorizationCacheUtil.class);
        authorizationService = AuthorizationService.getInstance();
    }

    public static AuthorizationCacheManager getInstance() {
        if (authorizationCacheManager == null) {
            authorizationCacheManager = new AuthorizationCacheManager();
        }
        return authorizationCacheManager;
    }

    public String getRedisKeyFromRequest(RequestContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        if (request == null) {
            return null;
        }

        String appSecret = StringUtils.defaultString(request.getHeader(RequestHeaders.SECRET_APP_KEY.getHeaderName()));
        String authToken = StringUtils.defaultString(request.getHeader(RequestHeaders.ACCESS_TOKEN.getHeaderName()));

        return appSecret.concat(authToken);
    }

    public AuthResponse getAuthResponseFromMS(RequestContext requestCtx) {
        AuthResponse authResponse = authorizationService.getAuthTokenFromAuthorizationMS(requestCtx);

        String redisKey = getRedisKeyFromRequest(requestCtx);

        authorizationCacheUtil.populateCache(redisKey, authResponse);

        return authResponse;
    }

    public AuthResponse getAuthResponseFromCache(RequestContext requestContext) {
        String redisKey = getRedisKeyFromRequest(requestContext);
        AuthResponse response = authorizationCacheUtil.getFromCache(redisKey);

        if (response == null) {
            response = getAuthResponseFromMS(requestContext);
        }

        return response;
    }

}
