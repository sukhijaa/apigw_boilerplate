package hyperdew.apigw.utilities;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestContextUtils {

    public static String getHeaderFromRequest(RequestContext requestCtx, String headerName) {
        HttpServletRequest request = requestCtx.getRequest();

        if (request == null) {
            return StringUtils.EMPTY;
        }

        String headerVal = StringUtils.defaultString(requestCtx.getZuulRequestHeaders().get(headerName));

        if (headerVal.isEmpty()) {
            return StringUtils.defaultString(request.getHeader(headerName));
        }

        return headerVal;
    }

    public static void addHeaderToRequest(RequestContext requestCtx, String headerName, String headerValue) {
        if (headerName == null || headerName.isEmpty()) {
            return;
        }

        requestCtx.addZuulRequestHeader(headerName, StringUtils.defaultString(headerValue));
        requestCtx.addZuulResponseHeader(headerName, StringUtils.defaultString(headerValue));
    }

    public static String getAppSecretFromRequest(RequestContext requestContext) {
        return getHeaderFromRequest(requestContext, RequestHeaders.SECRET_APP_KEY.getHeaderName());
    }

    public static String getAccessTokenFromRequest(RequestContext requestContext) {
        return getHeaderFromRequest(requestContext, RequestHeaders.ACCESS_TOKEN.getHeaderName());
    }

    private static String getRequestURLFromContext(RequestContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();

        if (request == null) {
            return StringUtils.EMPTY;
        }

        return StringUtils.defaultString(request.getRequestURI());
    }

    public static String getMSNameFromRequest(RequestContext requestContext) {
        String requestURL = getRequestURLFromContext(requestContext);

        if (requestURL.startsWith("/")) {
            requestURL = requestURL.replaceFirst("/", "");
        }

        int slashLocation = requestURL.indexOf("/");
        if (slashLocation != -1) {
            requestURL = requestURL.substring(0, slashLocation);
        }

        return StringUtils.defaultString(requestURL);
    }
}
