package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/26/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunInfoModel {

    private String fav_food;
    private String fav_song;
    private String fav_movie;
    private String fav_quote;
    private String fav_sport;
    private String embarr_moment;
    private String happy_moment;
    private String first_love;
    private String hw_lck_reaz;
    private String three_places;
    private String alien_xist;
    private String fiv_now;
    private String past_change;
    private String  opinion_me;
    private String best_mom_me;


    public String getFav_food() {
        return fav_food;
    }

    public void setFav_food(String fav_food) {
        this.fav_food = fav_food;
    }

    public String getFav_song() {
        return fav_song;
    }

    public void setFav_song(String fav_song) {
        this.fav_song = fav_song;
    }

    public String getFav_movie() {
        return fav_movie;
    }

    public void setFav_movie(String fav_movie) {
        this.fav_movie = fav_movie;
    }

    public String getFav_quote() {
        return fav_quote;
    }

    public void setFav_quote(String fav_quote) {
        this.fav_quote = fav_quote;
    }

    public String getFav_sport() {
        return fav_sport;
    }

    public void setFav_sport(String fav_sport) {
        this.fav_sport = fav_sport;
    }

    public String getEmbarr_moment() {
        return embarr_moment;
    }

    public void setEmbarr_moment(String embarr_moment) {
        this.embarr_moment = embarr_moment;
    }

    public String getHappy_moment() {
        return happy_moment;
    }

    public void setHappy_moment(String happy_moment) {
        this.happy_moment = happy_moment;
    }

    public String getFirst_love() {
        return first_love;
    }

    public void setFirst_love(String first_love) {
        this.first_love = first_love;
    }

    public String getHw_lck_reaz() {
        return hw_lck_reaz;
    }

    public void setHw_lck_reaz(String hw_lck_reaz) {
        this.hw_lck_reaz = hw_lck_reaz;
    }

    public String getThree_places() {
        return three_places;
    }

    public void setThree_places(String three_places) {
        this.three_places = three_places;
    }

    public String getAlien_xist() {
        return alien_xist;
    }

    public void setAlien_xist(String alien_xist) {
        this.alien_xist = alien_xist;
    }

    public String getFiv_now() {
        return fiv_now;
    }

    public void setFiv_now(String fiv_now) {
        this.fiv_now = fiv_now;
    }

    public String getPast_change() {
        return past_change;
    }

    public void setPast_change(String past_change) {
        this.past_change = past_change;
    }

    public String getOpinion_me() {
        return opinion_me;
    }

    public void setOpinion_me(String opinion_me) {
        this.opinion_me = opinion_me;
    }

    public String getBest_mom_me() {
        return best_mom_me;
    }

    public void setBest_mom_me(String best_mom_me) {
        this.best_mom_me = best_mom_me;
    }

}
