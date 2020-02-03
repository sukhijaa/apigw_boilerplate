package hyperdew.apigw.filters.preFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.utilities.RequestHeaders;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasePreFilterationTest {

    private BasePreFilteration basePreFilter;
    private RequestContext requestCtx;

    @BeforeEach
    void initialize() {
        basePreFilter = new BasePreFilteration();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        requestCtx = new RequestContext();
        requestCtx.setRequest(request);
        requestCtx.setResponse(response);
        requestCtx.setResponseStatusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldInterceptRequest() {
        assertEquals(true, basePreFilter.shouldInterceptRequest());
    }

    @Test
    void interceptRequest_validSecretAppKeyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(RequestHeaders.SECRET_APP_KEY.getHeaderName(), "testSecret");

        requestCtx.setRequest(request);
        basePreFilter.interceptRequest(requestCtx);

        assertEquals(HttpStatus.SC_OK, requestCtx.getResponseStatusCode());
    }

    @Test
    void interceptRequest_invalidSecretAppKeyTest() {
        basePreFilter.interceptRequest(requestCtx);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, requestCtx.getResponseStatusCode());
    }
}