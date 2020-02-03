package hyperdew.apigw.filters.preFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.utilities.RequestHeaders;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class BasePreFilteration {
    /**
     * Checks if need to filter this request or simply reverse proxy it
     */
    public boolean shouldInterceptRequest() {
        return true;
    }

    private void populateUnauthorizedResponse(RequestContext requestCtx) {
        //TODO - Replace with logger
        System.out.println("Unauthorized Request");

        requestCtx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        requestCtx.setResponseBody("Unauthorized to make this request. Please add a valid authentication header");
        requestCtx.setSendZuulResponse(false);
    }

    public void interceptRequest(RequestContext requestCtx) {
        HttpServletRequest request = requestCtx.getRequest();

        if (StringUtils.defaultString(request.getHeader(RequestHeaders.SECRET_APP_KEY.getHeaderName()), StringUtils.EMPTY).isEmpty()) {
            populateUnauthorizedResponse(requestCtx);
        }
    }
}
