package com.assetsmanager.dto;

import com.assetsmanager.model.Asset;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record AssetResponse(
        Long id,
        String name,
        String description,
        String category,
        LocalDate purchaseDate,
        BigDecimal purchasePrice,
        String notes,
        String imageUrl,
        Instant createdAt,
        Instant updatedAt
) {
    public static AssetResponse from(Asset a) {
        String imageUrl = a.getImageContentType() == null ? null : "/api/assets/" + a.getId() + "/image";
        return new AssetResponse(a.getId(), a.getName(), a.getDescription(), a.getCategory(),
                a.getPurchaseDate(), a.getPurchasePrice(), a.getNotes(), imageUrl,
                a.getCreatedAt(), a.getUpdatedAt());
    }
}
