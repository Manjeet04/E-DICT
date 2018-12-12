package edict;


import edict.lib.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Siddharth Narayan on 29-09-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)     // if there are extra keys then ignore them

public class EdictHomeResponse extends BaseResponse {


    private List<String> home_image_list,tab_list;



    public List<String> getHome_image_list() {
        return home_image_list;
    }

    public void setHome_image_list(List<String> home_image_list) {
        this.home_image_list = home_image_list;
    }

    public List<String> getTab_list() {
        return tab_list;
    }

    public void setTab_list(List<String> tab_list) {
        this.tab_list = tab_list;
    }


/*
    public void setBlood_donor_count(String group, int blood_donor) {
        blood_donor_count.put(group, blood_donor);
    }*/


}
