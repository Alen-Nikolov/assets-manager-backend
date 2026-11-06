package com.assetsmanager.controller;

import com.assetsmanager.dto.MaintenanceRequest;
import com.assetsmanager.dto.MaintenanceResponse;
import com.assetsmanager.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/assets/{assetId}/maintenance")
    public List<MaintenanceResponse> listForAsset(@PathVariable Long assetId) {
        return maintenanceService.findByAsset(assetId);
    }

    @PostMapping("/assets/{assetId}/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponse create(@PathVariable Long assetId, @Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.create(assetId, request);
    }

    @GetMapping("/maintenance/{id}")
    public MaintenanceResponse get(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @PutMapping("/maintenance/{id}")
    public MaintenanceResponse update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.update(id, request);
    }

    @DeleteMapping("/maintenance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        maintenanceService.delete(id);
    }
}
