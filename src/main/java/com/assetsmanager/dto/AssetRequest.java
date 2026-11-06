package com.assetsmanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssetRequest(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 2000) String description,
        String category,
        LocalDate purchaseDate,
        @DecimalMin("0.0") BigDecimal purchasePrice,
        String notes
) {
}
