package edict.lib.image_upload;

import edict.lib.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)     // if there are extra keys then ignore them
public class UploadImageResponse extends BaseResponse {
    private String url;
    private DataModel data;
    public UploadImageResponse() {
        url = null;
        data = null;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}