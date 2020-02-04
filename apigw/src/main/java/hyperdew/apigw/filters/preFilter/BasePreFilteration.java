package hyperdew.apigw.filters.preFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.authorization.AuthResponse;
import hyperdew.apigw.redis.AuthorizationCacheManager;
import hyperdew.apigw.utilities.RequestContextUtils;
import hyperdew.apigw.utilities.RequestHeaders;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.Date;


public class BasePreFilteration {

    private AuthorizationCacheManager authorizationCacheManager;
    private RequestContext requestCtx;
    private String ACCESS_TOKEN;
    private String APP_SECRET_KEY;

    public BasePreFilteration(RequestContext requestCtx) {
        this.requestCtx = requestCtx;
        authorizationCacheManager = AuthorizationCacheManager.getInstance();

        ACCESS_TOKEN = RequestContextUtils.getAccessTokenFromRequest(requestCtx);
        APP_SECRET_KEY = RequestContextUtils.getAppSecretFromRequest(requestCtx);
    }

    /**
     * Basic checks if need to filter this request or simply reverse proxy it
     */
    public boolean shouldInterceptRequest() {
        return true;
    }

    private void populateUnauthorizedResponse() {
        //TODO - Replace with logger
        System.out.println("Unauthorized Request");

        requestCtx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        requestCtx.setResponseBody("Unauthorized to make this request. Please add valid authentication headers as per API Contract");
        requestCtx.setSendZuulResponse(false);
    }

    /**
     * First checks if app-secret defined
     * If no, returns 401 Unauthorized
     * Then checks if access-token is defined
     * If no, calls authorization MS
     */
    private void performBasicHeaderChecks() {

        if (APP_SECRET_KEY.isEmpty()) {
            populateUnauthorizedResponse();
        }

        if (ACCESS_TOKEN.isEmpty()) {
            updateAccessTokenInHeader();
        }
    }

    private void updateAccessTokenInHeader() {
        AuthResponse authResponse = authorizationCacheManager.getAuthResponseFromMS(requestCtx);

        if (authResponse == null || StringUtils.defaultString(authResponse.getAccessToken()).isEmpty()) {
            populateUnauthorizedResponse();
        }

        RequestContextUtils.addHeaderToRequest(requestCtx, RequestHeaders.ACCESS_TOKEN.getHeaderName(), authResponse.getAccessToken());
    }

    private void validateTokenExpiry(AuthResponse authResponse) {
        if (authResponse.getTokenExpiry().after(new Date())) {
            updateAccessTokenInHeader();
        }
    }

    private void checkIfHeadersAreValid() {
        AuthResponse authResponse = authorizationCacheManager.getAuthResponseFromCache(requestCtx);

        validateTokenExpiry(authResponse);

    }

    private void performAuthentication() {
        performBasicHeaderChecks();
        checkIfHeadersAreValid();
    }

    public void interceptRequest() {
        performAuthentication();
    }
}
