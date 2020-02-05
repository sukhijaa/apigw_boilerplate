package hyperdew.apigw.filters.preFilter;

public interface PreFilteration {

    /**
     * Basic checks if need to filter this request or simply reverse proxy it
     */
    boolean shouldInterceptRequest();

    /**
     * Perform the filteration logic here
     */
    void interceptRequest();
}
