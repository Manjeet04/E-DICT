package edict.lib.image_upload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)     // if there are extra keys then ignore them
public class DataModel {
    private String url;
    private HashMap<String, String> fields;
    DataModel() {
        url = null;
        fields = null;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }
}
