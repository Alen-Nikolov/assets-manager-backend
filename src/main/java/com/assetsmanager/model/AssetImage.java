package com.assetsmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Image bytes for an asset, kept in its own table so listing assets never loads them. Id = asset id. */
@Entity
@Table(name = "asset_images")
@Getter
@Setter
@NoArgsConstructor
public class AssetImage {

    @Id
    private Long assetId;

    @Column(nullable = false)
    private byte[] data;

    public AssetImage(Long assetId) {
        this.assetId = assetId;
    }
}
