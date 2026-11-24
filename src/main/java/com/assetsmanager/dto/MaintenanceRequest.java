package com.assetsmanager.dto;

import com.assetsmanager.model.MaintenanceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceRequest(
        @NotNull MaintenanceType type,
        @Size(max = 2000) String description,
        String performedBy,
        @NotNull LocalDate maintenanceDate,
        @DecimalMin("0.0") BigDecimal cost,
        LocalDate nextDueDate
) {
}
