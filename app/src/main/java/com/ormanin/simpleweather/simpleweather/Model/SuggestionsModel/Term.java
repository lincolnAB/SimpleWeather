
package com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Term {

    @SerializedName("offset")
    @Expose
    private Double offset;
    @SerializedName("value")
    @Expose
    private String value;

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
