package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import edict.lib.network.BaseResponse;

/**
 * Created by Manjeet Singh on 7/26/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResultModel extends BaseResponse {

    ArrayList<TokenResponseModel> token;

    public ArrayList<TokenResponseModel> getToken() {
        return token;
    }

    public void setToken(ArrayList<TokenResponseModel> token) {
        this.token = token;
    }
}
