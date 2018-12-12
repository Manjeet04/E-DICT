package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/25/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenModel {

    private String email;
    private int token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
