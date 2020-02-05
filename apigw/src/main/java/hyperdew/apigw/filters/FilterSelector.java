package hyperdew.apigw.filters;

import com.netflix.zuul.context.RequestContext;

public interface FilterSelector {
    public Object getFilter(RequestContext requestCtx);
}
