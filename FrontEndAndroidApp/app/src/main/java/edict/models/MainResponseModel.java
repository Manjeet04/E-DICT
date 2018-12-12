package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainResponseModel {

    private FullResponseModel response;

    public FullResponseModel getResponse() {
        return response;
    }

    public void setResponse(FullResponseModel response) {
        this.response = response;
    }
}
