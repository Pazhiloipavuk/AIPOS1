package aipos1.enumeration;

public enum HttpMethod {

    GET(Boolean.FALSE, Boolean.TRUE),
    HEAD(Boolean.FALSE, Boolean.FALSE),
    POST(Boolean.TRUE, Boolean.TRUE),
    PUT(Boolean.TRUE, Boolean.TRUE),
    DELETE(Boolean.TRUE, Boolean.TRUE),
    OPTIONS(Boolean.FALSE, Boolean.TRUE),
    TRACE(Boolean.FALSE, Boolean.FALSE),
    PATCH(Boolean.TRUE, Boolean.TRUE);

    private Boolean hasRequestBody;

    private Boolean hasResponseBody;

    public Boolean hasRequestBody() {
        return hasRequestBody;
    }

    public Boolean hasResponseBody() {
        return hasResponseBody;
    }

    HttpMethod(Boolean hasRequestBody, Boolean hasResponseBody) {
        this.hasRequestBody = hasRequestBody;
        this.hasResponseBody = hasResponseBody;
    }
}
