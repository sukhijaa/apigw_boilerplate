package hyperdew.apigw.filters.postFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.filters.FilterSelector;
import hyperdew.apigw.utilities.KnownMSNames;
import hyperdew.apigw.utilities.RequestContextUtils;

public class PostFilterSelector implements FilterSelector {
    @Override
    public Object getFilter(RequestContext requestCtx) {
        String msName = RequestContextUtils.getMSNameFromRequest(requestCtx);
        KnownMSNames knownMSName = KnownMSNames.getEnumForValue(msName);

        switch (knownMSName) {
            case AUTHORIZATION_MS:
                return new AuthorizationMSPostFilteration();
            default:
                return new BasePostFiltration(requestCtx);
        }
    }
}
