package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullResponseModel {

    private WordModel personal_info;
    private AddressInfoModel address_info;
    private FunInfoModel fun_info;

    public WordModel getPersonal_info() {
        return personal_info;
    }

    public void setPersonal_info(WordModel personal_info) {
        this.personal_info = personal_info;
    }

    public AddressInfoModel getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressInfoModel address_info) {
        this.address_info = address_info;
    }

    public FunInfoModel getFun_info() {
        return fun_info;
    }

    public void setFun_info(FunInfoModel fun_info) {
        this.fun_info = fun_info;
    }
}
