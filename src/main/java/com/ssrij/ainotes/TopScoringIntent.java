package com.ssrij.ainotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopScoringIntent {

    @SerializedName("intent")
    @Expose
    private String intent;
    @SerializedName("score")
    @Expose
    private Double score;

    /**
     *
     * @return
     * The intent
     */
    public String getIntent() {
        return intent;
    }

    /**
     *
     * @param intent
     * The intent
     */
    public void setIntent(String intent) {
        this.intent = intent;
    }

    /**
     *
     * @return
     * The score
     */
    public Double getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(Double score) {
        this.score = score;
    }

}