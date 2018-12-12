package edict.models;

import java.util.List;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public class WordSearchModel {
    public String msg;
    private List<WordModel> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WordModel> getResult() {
        return result;
    }

    public void setResult(List<WordModel> result) {
        this.result = result;
    }
}
