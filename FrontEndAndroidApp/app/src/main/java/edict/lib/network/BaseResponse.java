package edict.lib.network;

/**
 * Created by Necra on 23-02-2018.
 */

public class BaseResponse {

    protected String response;
    protected String error;
    protected String message;
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private static final String SUCCESS_RESPONSE = "success";

    public BaseResponse() {
        response = null;
        error = "No response available";
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccessful() {
        return response.equalsIgnoreCase(SUCCESS_RESPONSE);
    }

    public String getMessage() {
        return message;
    }
}
