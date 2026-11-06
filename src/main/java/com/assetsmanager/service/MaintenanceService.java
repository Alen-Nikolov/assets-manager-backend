package com.assetsmanager.service;

import com.assetsmanager.dto.MaintenanceRequest;
import com.assetsmanager.dto.MaintenanceResponse;
import com.assetsmanager.exception.NotFoundException;
import com.assetsmanager.model.Asset;
import com.assetsmanager.model.MaintenanceRecord;
import com.assetsmanager.repository.AssetRepository;
import com.assetsmanager.repository.MaintenanceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRecordRepository recordRepository;
    private final AssetRepository assetRepository;

    @Transactional(readOnly = true)
    public List<MaintenanceResponse> findByAsset(Long assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new NotFoundException("Asset not found: " + assetId);
        }
        return recordRepository.findByAssetIdOrderByMaintenanceDateDesc(assetId)
                .stream().map(MaintenanceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public MaintenanceResponse findById(Long id) {
        return MaintenanceResponse.from(getRecord(id));
    }

    public MaintenanceResponse create(Long assetId, MaintenanceRequest request) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new NotFoundException("Asset not found: " + assetId));
        MaintenanceRecord record = new MaintenanceRecord();
        record.setAsset(asset);
        apply(record, request);
        return MaintenanceResponse.from(recordRepository.save(record));
    }

    public MaintenanceResponse update(Long id, MaintenanceRequest request) {
        MaintenanceRecord record = getRecord(id);
        apply(record, request);
        return MaintenanceResponse.from(record);
    }

    public void delete(Long id) {
        recordRepository.delete(getRecord(id));
    }

    private MaintenanceRecord getRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Maintenance record not found: " + id));
    }

    private void apply(MaintenanceRecord record, MaintenanceRequest r) {
        record.setType(r.type());
        record.setDescription(r.description());
        record.setPerformedBy(r.performedBy());
        record.setMaintenanceDate(r.maintenanceDate());
        record.setCost(r.cost());
        record.setNextDueDate(r.nextDueDate());
    }
}
