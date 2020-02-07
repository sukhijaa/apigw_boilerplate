package hyperdew.authservice.utils;

public enum RequestHeaders {
    SECRET_APP_KEY("app-secret"),
    ACCESS_TOKEN("access-token"),
    AUTHORIZATION("authorization");

    private String headerValue;

    RequestHeaders(final String value) {
        this.headerValue = value;
    }

    public String getHeaderName() {
        return this.headerValue;
    }
}
