package hyperdew.authservice.utils;

import hyperdew.authservice.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestContextUtils {

    public static String getAccessTokenFromRequest(HttpServletRequest request) {
        return getAccessTokenFromRequestHeader(request.getHeader(RequestHeaders.ACCESS_TOKEN.getHeaderName()));
    }

    public static String getAccessTokenFromRequestHeader(String requestHeader) {
        requestHeader = Optional.ofNullable(requestHeader).orElseThrow(() -> new InvalidTokenException("Access Token not found in header"));
        if (!requestHeader.startsWith("Bearer")) {
            throw new InvalidTokenException("Invalid Token Format found. Token must start with Bearer");
        }

        return requestHeader.substring(7);
    }
}
