package com.pictureworks.zensmartjpa.persistence;

import com.pictureworks.zensmartjpa.model.StringJsonUserType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "an_dashboards_reports")
@TypeDefs( {@TypeDef( name= "StringJsonObject", typeClass = StringJsonUserType.class)})
public class DashboardReports {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="dashboard_reports")
    @SequenceGenerator(
            name="dashboard_reports",
            sequenceName="an_dashboards_reports_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboards dashboards;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Reports reports;

    @Type(type = "StringJsonObject")
    @Column(name = "layout")
    private String layout;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
