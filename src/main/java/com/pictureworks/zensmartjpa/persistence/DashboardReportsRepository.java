package com.pictureworks.zensmartjpa.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DashboardReportsRepository extends CrudRepository<DashboardReports, Long> {
    List<DashboardReports> findByDashboards_Id(Long id);
}
