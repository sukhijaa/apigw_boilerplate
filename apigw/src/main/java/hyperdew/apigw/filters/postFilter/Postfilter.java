package hyperdew.apigw.filters.postFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import hyperdew.apigw.utilities.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class Postfilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(Postfilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return ConfigConstants.FILTER_PRECEDENCE_HIGHEST;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.debug("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

        PostFiltration filter = (PostFiltration) new PostFilterSelector().getFilter(ctx);

        if (filter.shouldInterceptRequest()) {
            filter.interceptRequest();
        }

        return null;
    }
}
