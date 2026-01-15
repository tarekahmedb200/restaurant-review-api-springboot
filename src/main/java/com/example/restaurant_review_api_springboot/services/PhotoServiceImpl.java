package com.example.restaurant_review_api_springboot.services;

import com.example.restaurant_review_api_springboot.domain.entites.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final  StorageService storageService;

    @Autowired
    public PhotoServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Photo uploadPhoto(MultipartFile file) {
        String photoId = UUID.randomUUID().toString();
        var url = storageService.store(file,photoId);

        return  Photo
                .builder()
                .url(url)
                .uploadDate(LocalDateTime.now())
                .build();
    }

    @Override
    public Optional<Resource> getPhotoAsResource(String id) {
       return storageService.loadAsResource(id);
    }
}
