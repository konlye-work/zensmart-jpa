package com.pictureworks.zensmartjpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardLayout {
    @JsonProperty("i")
    private String i;
    @JsonProperty("x")
    private Integer x;
    @JsonProperty("y")
    private Integer y;
    @JsonProperty("w")
    private Integer w;
    @JsonProperty("h")
    private Integer h;
    @JsonProperty("minW")
    private Integer minW;
    @JsonProperty("minH")
    private Integer minH;
}
