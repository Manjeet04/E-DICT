package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/26/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressInfoModel {


    private String address;
    private String city;
    private String state;
    private String country;
    private String landmark;
    private int pin_code;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public int getPin_code() {
        return pin_code;
    }

    public void setPin_code(int pin_code) {
        this.pin_code = pin_code;
    }
}
