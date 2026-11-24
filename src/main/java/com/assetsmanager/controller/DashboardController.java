package com.assetsmanager.controller;

import com.assetsmanager.dto.AssetResponse;
import com.assetsmanager.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public List<AssetResponse> getAllAssets() {
        return assetService.findAll();
    }
}
