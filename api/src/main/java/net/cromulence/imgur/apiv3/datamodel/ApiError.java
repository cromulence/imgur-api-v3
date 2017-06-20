package net.cromulence.imgur.apiv3.datamodel;

public class ApiError {

    /*
     * Samples of Error JSON
     * Bad image {!error":"Image format not supported, or image is corrupt.","request":"\/3\/image","method":"POST"}
     * timeout {"error":"Operation timed out after 10ith 0 bytes received","request":"\/3\/gallery\/hot\/viral\/366","method":"GET"}}
     * too big "request":"\/3\/image","method":"POST","data":{"error":{"code":1002,"message":"File is over the size limit","type":"ImgurException","exception":[] },},
     */

    private ErrorDetails errorDetails;
    private String request;
    private String method;

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(ErrorDetails errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return String.format("ApiError{errorDetails=%s, request='%s', method='%s'}", errorDetails, request, method);
    }
}
