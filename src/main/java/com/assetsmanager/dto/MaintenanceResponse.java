package com.assetsmanager.dto;

import com.assetsmanager.model.MaintenanceRecord;
import com.assetsmanager.model.MaintenanceType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record MaintenanceResponse(
        Long id,
        Long assetId,
        MaintenanceType type,
        String description,
        String performedBy,
        LocalDate maintenanceDate,
        BigDecimal cost,
        LocalDate nextDueDate,
        Instant createdAt
) {
    public static MaintenanceResponse from(MaintenanceRecord r) {
        return new MaintenanceResponse(r.getId(), r.getAsset().getId(), r.getType(), r.getDescription(),
                r.getPerformedBy(), r.getMaintenanceDate(), r.getCost(), r.getNextDueDate(), r.getCreatedAt());
    }
}
