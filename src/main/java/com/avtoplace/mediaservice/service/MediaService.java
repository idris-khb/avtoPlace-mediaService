package com.avtoplace.mediaservice.service;

import com.avtoplace.mediaservice.model.CarAd;
import com.avtoplace.mediaservice.model.CarPhoto;
import com.avtoplace.mediaservice.repository.CarAdRepository;
import com.avtoplace.mediaservice.repository.CarPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final CarAdRepository carAdRepository;
    private final CarPhotoRepository carPhotoRepository;
    private final S3Client s3Client;

    @org.springframework.beans.factory.annotation.Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Transactional
    public CarAd saveCarAdWithPhotos(CarAd ad, List<MultipartFile> photos) throws Exception {
        // Сохраняем объявление
        CarAd savedAd = carAdRepository.save(ad);

        ensureBucketExists();

        for (MultipartFile file : photos) {
            String key = file.getOriginalFilename();
            try (InputStream input = file.getInputStream()) {
                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentLength(file.getSize())
                        .contentType(file.getContentType())
                        .build();
                s3Client.putObject(request, RequestBody.fromInputStream(input, file.getSize()));
            }

            // Сохраняем ссылку на фото в базе
            CarPhoto photo = new CarPhoto();
            photo.setFilename(key);
            photo.setCarAd(savedAd);
            carPhotoRepository.save(photo);
        }

        return savedAd;
    }

    private void ensureBucketExists() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (Exception e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }

    public List<CarAd> getAllCarAds() {
        return carAdRepository.findAll();
    }

    public CarAd getCarAdById(UUID id) {
        return carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CarAd не найден"));
    }

    public void uploadFile(String key, InputStream inputStream, long size, String contentType) {
        ensureBucketExists();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentLength(size)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, size));
    }

    public void deleteCarAd(UUID id) {
        CarAd carAd = carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CarAd not found"));

        // Удаляем все фото из S3
        for (CarPhoto photo : carAd.getPhotos()) {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(photo.getFilename())
                    .build());
        }

        // Удаляем CarPhoto из базы
        carPhotoRepository.deleteAll(carAd.getPhotos());

        // Удаляем сам CarAd
        carAdRepository.delete(carAd);
    }
}
