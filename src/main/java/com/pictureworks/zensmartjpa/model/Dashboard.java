package com.pictureworks.zensmartjpa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Dashboard {
    private Long id;
    private String name;
    private List<DashboardReport> dashboard_reports;
}
