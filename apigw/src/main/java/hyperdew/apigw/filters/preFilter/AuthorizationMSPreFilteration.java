package hyperdew.apigw.filters.preFilter;

public class AuthorizationMSPreFilteration implements PreFilteration {

    @Override
    public boolean shouldInterceptRequest() {
        return false;
    }

    @Override
    public void interceptRequest() {

    }

}
