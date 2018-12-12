package edict.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MostSearchModel {

    //private String image_url;
    private String word;
    private String meaning;
    private String synonym;
    private String antonym;
    private String word_origin;
    private String example;
    private float hit_points;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public String getWord_origin() {
        return word_origin;
    }

    public void setWord_origin(String word_origin) {
        this.word_origin = word_origin;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public float getHit_points() {
        return hit_points;
    }

    public void setHit_points(float hit_points) {
        this.hit_points = hit_points;
    }
}
