package lk.ijse.dep.web.exception;

public class HttpResponseException extends RuntimeException{

    private int statusCode;

    public HttpResponseException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.setStatusCode(statusCode);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
