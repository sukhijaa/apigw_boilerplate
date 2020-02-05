package hyperdew.apigw.filters.postFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import hyperdew.apigw.utilities.ConfigConstants;

public class Postfilter extends ZuulFilter {
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
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}