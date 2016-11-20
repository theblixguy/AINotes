package com.ssrij.ainotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LUISResponse {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("topScoringIntent")
    @Expose
    private TopScoringIntent topScoringIntent;
    @SerializedName("entities")
    @Expose
    private List<Entity> entities = new ArrayList<Entity>();

    /**
     *
     * @return
     * The query
     */
    public String getQuery() {
        return query;
    }

    /**
     *
     * @param query
     * The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     *
     * @return
     * The topScoringIntent
     */
    public TopScoringIntent getTopScoringIntent() {
        return topScoringIntent;
    }

    /**
     *
     * @param topScoringIntent
     * The topScoringIntent
     */
    public void setTopScoringIntent(TopScoringIntent topScoringIntent) {
        this.topScoringIntent = topScoringIntent;
    }

    /**
     *
     * @return
     * The entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     *
     * @param entities
     * The entities
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

}