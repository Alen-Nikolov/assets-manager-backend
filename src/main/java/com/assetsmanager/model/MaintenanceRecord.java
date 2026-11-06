package com.assetsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType type;

    @Column(length = 2000)
    private String description;

    private String performedBy;

    @Column(nullable = false)
    private LocalDate maintenanceDate;

    @Column(precision = 12, scale = 2)
    private BigDecimal cost;

    private LocalDate nextDueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
}
