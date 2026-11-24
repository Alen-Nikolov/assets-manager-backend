package com.assetsmanager.repository;

import com.assetsmanager.model.AssetImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetImageRepository extends JpaRepository<AssetImage, Long> {
}
