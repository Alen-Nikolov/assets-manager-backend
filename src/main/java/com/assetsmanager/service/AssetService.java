package com.assetsmanager.service;

import com.assetsmanager.dto.AssetRequest;
import com.assetsmanager.dto.AssetResponse;
import com.assetsmanager.dto.ImageContent;
import com.assetsmanager.exception.BadRequestException;
import com.assetsmanager.exception.NotFoundException;
import com.assetsmanager.model.Asset;
import com.assetsmanager.model.AssetImage;
import com.assetsmanager.repository.AssetImageRepository;
import com.assetsmanager.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {

    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

    private final AssetRepository assetRepository;
    private final AssetImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream().map(AssetResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AssetResponse findById(Long id) {
        return AssetResponse.from(getAsset(id));
    }

    public AssetResponse create(AssetRequest request) {
        Asset asset = new Asset();
        apply(asset, request);
        return AssetResponse.from(assetRepository.save(asset));
    }

    public AssetResponse update(Long id, AssetRequest request) {
        Asset asset = getAsset(id);
        apply(asset, request);
        return AssetResponse.from(asset);
    }

    public void delete(Long id) {
        Asset asset = getAsset(id);
        imageRepository.deleteById(id);
        assetRepository.delete(asset);
    }

    public AssetResponse saveImage(Long id, MultipartFile file) {
        Asset asset = getAsset(id);
        String contentType = file.getContentType();
        if (file.isEmpty()) {
            throw new BadRequestException("Image file is empty");
        }
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType)) {
            throw new BadRequestException("Unsupported image type. Allowed: " + ALLOWED_IMAGE_TYPES);
        }
        AssetImage image = imageRepository.findById(id).orElseGet(() -> new AssetImage(id));
        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        imageRepository.save(image);
        asset.setImageContentType(contentType);
        return AssetResponse.from(asset);
    }

    @Transactional(readOnly = true)
    public ImageContent getImage(Long id) {
        Asset asset = getAsset(id);
        if (asset.getImageContentType() == null) {
            throw new NotFoundException("Asset has no image: " + id);
        }
        AssetImage image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset has no image: " + id));
        return new ImageContent(asset.getImageContentType(), image.getData());
    }

    public void deleteImage(Long id) {
        Asset asset = getAsset(id);
        imageRepository.deleteById(id);
        asset.setImageContentType(null);
    }

    private Asset getAsset(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset not found: " + id));
    }

    private void apply(Asset asset, AssetRequest r) {
        asset.setName(r.name());
        asset.setDescription(r.description());
        asset.setCategory(r.category());
        asset.setPurchaseDate(r.purchaseDate());
        asset.setPurchasePrice(r.purchasePrice());
        asset.setNotes(r.notes());
    }
}
