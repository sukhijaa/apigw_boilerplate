package hyperdew.apigw.filters.postFilter;

import com.netflix.zuul.context.RequestContext;

public class BasePostFiltration implements PostFiltration {

    private RequestContext requestContext;

    public BasePostFiltration(RequestContext ctx) {
        this.requestContext = ctx;
    }

    @Override
    public boolean shouldInterceptRequest() {
        return false;
    }

    @Override
    public void interceptRequest() {
    }
}
