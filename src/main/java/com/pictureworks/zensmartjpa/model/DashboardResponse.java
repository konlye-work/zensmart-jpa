package com.pictureworks.zensmartjpa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardResponse implements ResponseData{
    List<Dashboard> dashboards;
}
