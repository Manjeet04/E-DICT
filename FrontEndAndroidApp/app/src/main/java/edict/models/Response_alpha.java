package edict.models;

import java.util.List;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */

public class Response_alpha {

    private List<MostSearchModel> response; // "response" is name of " " from backend and thn right click -> generate -> getter and setter

    public List<MostSearchModel> getResponse() {
        return response;
    }

    public void setResponse(List<MostSearchModel> response) {
        this.response = response;
    }
}