package com.pictureworks.zensmartjpa.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pictureworks.zensmartjpa.model.*;
import com.pictureworks.zensmartjpa.persistence.*;
import com.pictureworks.zensmartjpa.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    @Autowired
    private DashboardsRepository dashboardsRepository;

    @Autowired
    private DashboardReportsRepository dashboardReportsRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
    }

    @GetMapping("/api/dashboards/all")
    public ResponseEntity<Response> getAllDashboards(){
        List<Dashboards> dashboards = (List<Dashboards>) dashboardsRepository.findAll();
        Map<Long, List<DashboardReports>> dashboardReportsMap = new HashMap<>();

        dashboardReportsRepository.findAll().forEach(dashboardReport -> {
            Long dashboardId = dashboardReport.getDashboards().getId();
            List<DashboardReports> dashboardReports = dashboardReportsMap.computeIfAbsent(dashboardId, k -> new ArrayList<>());
            dashboardReports.add(dashboardReport);
        });

        Response response = ResponseUtil.getResponse(createResponseWrapper(dashboards, dashboardReportsMap));

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/api/dashboards/{id}")
    public ResponseEntity<Response> getDashboard(@PathVariable Long id){
        Response response = ResponseUtil.getResponse(HttpStatus.OK, "No record found");
        Dashboards dashboardsObj = dashboardsRepository.findById(id).orElse(null);

        if (dashboardsObj != null){
            List<DashboardReports> dashboardReports = dashboardReportsRepository.findByDashboards_Id(dashboardsObj.getId());
            response = ResponseUtil.getResponse(createResponseWrapper(dashboardsObj, dashboardReports));
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/api/dashboards")
    public ResponseEntity<Response> createDashboard(@RequestBody Dashboard obj){
        Response response = save(obj,false);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/api/dashboards")
    public ResponseEntity<Response> updateDashboard(@RequestBody Dashboard obj){
        Response response = save(obj,true);
        return new ResponseEntity<>(response, response.getStatus());
    }

    private Response save(Dashboard obj, boolean isUpdate){
        Dashboards dashboard = new Dashboards();
        List<DashboardReports> dashboardReports = new ArrayList<>();
        LocalDateTime currentTimestamp = LocalDateTime.now();

        try{
            if (obj != null){
                if (isUpdate){
                    Long dashboardId = obj.getId();
                    Dashboards dashboardsObj = dashboardsRepository.findById(dashboardId).orElse(null);

                    if (dashboardsObj == null){
                        return ResponseUtil.getResponse(HttpStatus.OK, "Record not found");
                    }

                    dashboard.setId(dashboardId);
                }

                dashboard.setName(obj.getName());
                dashboard.setCreatedAt(currentTimestamp);
                dashboard.setUpdatedAt(currentTimestamp);
                dashboard = dashboardsRepository.save(dashboard);

                for (DashboardReport report : obj.getDashboard_reports()){
                    DashboardReports dashboardReport = new DashboardReports();
                    Long dashboardReportId = report.getId();

                    if (dashboardReportId != null) {
                        dashboardReport = dashboardReportsRepository.findById(dashboardReportId).orElse(null);
                        dashboardReport.setId(dashboardReport.getId());
                    }

                    dashboardReport.setDashboards(dashboard);
                    Reports reportObj = reportsRepository.findById(report.getReports_id()).orElse(null);
                    dashboardReport.setReports(reportObj);
                    dashboardReport.setLayout(mapper.writeValueAsString(report.getLayout()));
                    dashboardReport.setCreatedAt(currentTimestamp);
                    dashboardReport.setUpdatedAt(currentTimestamp);
                    dashboardReports.add(dashboardReport);
                }

                dashboardReports = (List<DashboardReports>) dashboardReportsRepository.saveAll(dashboardReports);
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseUtil.getResponse(createResponseWrapper(dashboard, dashboardReports));
    }

    private DashboardResponse createResponseWrapper(Dashboards dashboardsObj, List<DashboardReports> dashboardReports){
        DashboardResponse responseWrapper = new DashboardResponse();
        List<Dashboard> dashboards = new ArrayList<>();
        Dashboard dashboardWrapper = new Dashboard();
        dashboards.add(dashboardWrapper);
        responseWrapper.setDashboards(dashboards);

        if (dashboardsObj != null){
            dashboardWrapper.setId(dashboardsObj.getId());
            dashboardWrapper.setName(dashboardsObj.getName());
            dashboardWrapper.setDashboard_reports(getDashboardReports(dashboardReports));
        }

        return responseWrapper;
    }

    private DashboardResponse createResponseWrapper(List<Dashboards> dashboardsList, Map<Long, List<DashboardReports>> dashboardReportsMap){
        DashboardResponse responseWrapper = new DashboardResponse();
        List<Dashboard> dashboards = new ArrayList<>();
        responseWrapper.setDashboards(dashboards);

        for (Dashboards dashboardsObj : dashboardsList){
            Dashboard dashboardWrapper = new Dashboard();
            Long dashboardId = dashboardsObj.getId();
            List<DashboardReports> dashboardReports = dashboardReportsMap.get(dashboardId);
            dashboardWrapper.setId(dashboardId);
            dashboardWrapper.setName(dashboardsObj.getName());
            dashboardWrapper.setDashboard_reports(getDashboardReports(dashboardReports));
            dashboards.add(dashboardWrapper);
        }

        return responseWrapper;
    }

    private List<DashboardReport> getDashboardReports(List<DashboardReports> dashboardReports){
        List<DashboardReport> dashboardReportList = new ArrayList<>();

        try{
            for (DashboardReports dashboardReport : dashboardReports){
                DashboardReport dr = new DashboardReport();
                dr.setId(dashboardReport.getId());
                dr.setDashboard_id(dashboardReport.getDashboards().getId());
                dr.setReports_id(dashboardReport.getReports().getId());
                dr.setLayout(mapper.readValue(dashboardReport.getLayout(),DashboardLayout.class));
                dashboardReportList.add(dr);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return dashboardReportList;
    }
}
