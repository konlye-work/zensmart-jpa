package com.pictureworks.zensmartjpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardReport {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("dashboard_id")
    private Long dashboard_id;
    @JsonProperty("reports_id")
    private Long reports_id;
    @JsonProperty("layout")
    private DashboardLayout layout;
}
