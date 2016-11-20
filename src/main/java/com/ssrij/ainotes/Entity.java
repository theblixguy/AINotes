package com.ssrij.ainotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entity {

    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("endIndex")
    @Expose
    private Integer endIndex;
    @SerializedName("score")
    @Expose
    private Double score;

    /**
     *
     * @return
     * The entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     *
     * @param entity
     * The entity
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The startIndex
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     * The startIndex
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     * The endIndex
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     *
     * @param endIndex
     * The endIndex
     */
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
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