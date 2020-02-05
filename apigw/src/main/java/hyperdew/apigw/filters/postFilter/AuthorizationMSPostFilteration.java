package hyperdew.apigw.filters.postFilter;

public class AuthorizationMSPostFilteration implements PostFiltration {
    @Override
    public boolean shouldInterceptRequest() {
        return false;
    }

    @Override
    public void interceptRequest() {

    }
}
