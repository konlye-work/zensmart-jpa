package com.pictureworks.zensmartjpa.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "an_dashboards")
public class Dashboards {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dashboards")
    @SequenceGenerator(
        name="dashboards",
        sequenceName="an_dashboards_id_seq",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}