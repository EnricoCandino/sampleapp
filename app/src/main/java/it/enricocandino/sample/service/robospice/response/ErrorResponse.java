package it.enricocandino.sample.service.robospice.response;

/**
 * Created by enrico on 20/02/15.
 */
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
