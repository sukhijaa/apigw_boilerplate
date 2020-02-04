package hyperdew.apigw.filters.preFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.authorization.AuthResponse;
import hyperdew.apigw.redis.AuthorizationCacheManager;
import hyperdew.apigw.utilities.RequestHeaders;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public class BasePreFilteration {

    private AuthorizationCacheManager authorizationCacheManager;
    private RequestContext requestCtx;

    public BasePreFilteration(RequestContext requestCtx) {
        this.requestCtx = requestCtx;
        authorizationCacheManager = AuthorizationCacheManager.getInstance();
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
        HttpServletRequest request = requestCtx.getRequest();

        if (StringUtils.defaultString(request.getHeader(RequestHeaders.SECRET_APP_KEY.getHeaderName())).isEmpty()) {
            populateUnauthorizedResponse();
        }

        if (StringUtils.defaultString(request.getHeader(RequestHeaders.ACCESS_TOKEN.getHeaderName())).isEmpty()) {
            updateAccessTokenInHeader();
        }
    }

    private void updateAccessTokenInHeader() {
        AuthResponse authResponse = authorizationCacheManager.getAuthResponseFromMS(requestCtx);

        if (authResponse == null || StringUtils.defaultString(authResponse.getAccessToken()).isEmpty()) {
            populateUnauthorizedResponse();
        }

        requestCtx.addZuulRequestHeader(RequestHeaders.ACCESS_TOKEN.getHeaderName(), authResponse.getAccessToken());

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
