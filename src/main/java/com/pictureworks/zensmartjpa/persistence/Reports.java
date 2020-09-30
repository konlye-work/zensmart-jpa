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
@Table(name = "an_reports")
@TypeDefs( {@TypeDef( name= "StringJsonObject", typeClass = StringJsonUserType.class)})
public class Reports {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="reports")
    @SequenceGenerator(
            name="reports",
            sequenceName="an_reports_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "table")
    private String table;

    @Type(type = "StringJsonObject")
    @Column(name = "primary_filter")
    private String primaryFilter;

    @Type(type = "StringJsonObject")
    @Column(name = "secondary_filter")
    private String secondaryFilter;

    @Type(type = "StringJsonObject")
    @Column(name = "bucket_fields")
    private String bucketFields;

    @Type(type = "StringJsonObject")
    @Column(name = "fields")
    private String fields;

    @Column(name = "visualization")
    private String visualization;

    @Type(type = "StringJsonObject")
    @Column(name = "groups")
    private String groups;

    @Type(type = "StringJsonObject")
    @Column(name = "chart_configuration")
    private String chart_configuration;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
