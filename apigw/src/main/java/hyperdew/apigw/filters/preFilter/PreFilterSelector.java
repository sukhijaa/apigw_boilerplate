package hyperdew.apigw.filters.preFilter;

import com.netflix.zuul.context.RequestContext;
import hyperdew.apigw.filters.FilterSelector;
import hyperdew.apigw.utilities.KnownMSNames;
import hyperdew.apigw.utilities.RequestContextUtils;

public class PreFilterSelector implements FilterSelector {

    @Override
    public Object getFilter(RequestContext requestCtx) {
        String msName = RequestContextUtils.getMSNameFromRequest(requestCtx);
        KnownMSNames knownMS = KnownMSNames.getEnumForValue(msName);

        switch (knownMS) {
            case AUTHORIZATION_MS: {
                return new AuthorizationMSPreFilteration();
            }
            default:
                return new BasePreFilteration(requestCtx);
        }

    }
}
