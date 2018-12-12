package edict.models;

import java.util.List;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public class WordListResponse {

    private String msg;
    private List<WordModel> respose;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WordModel> getRespose() {
        return respose;
    }

    public void setRespose(List<WordModel> respose) {
        this.respose = respose;
    }
}
