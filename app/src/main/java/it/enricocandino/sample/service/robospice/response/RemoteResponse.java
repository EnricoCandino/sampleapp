package it.enricocandino.sample.service.robospice.response;

/**
 * Created by enrico on 20/02/15.
 */
public class RemoteResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
