package hyperdew.apigw.filters.preFilter;

import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        // TODO - Get Header name from Enum
        if (StringUtils.defaultString(request.getHeader("auth"), StringUtils.EMPTY).isEmpty()) {
            populateUnauthorizedResponse(requestCtx);
        }
    }
}
