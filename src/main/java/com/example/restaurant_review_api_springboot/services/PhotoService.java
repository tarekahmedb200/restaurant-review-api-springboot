package com.example.restaurant_review_api_springboot.services;


import com.example.restaurant_review_api_springboot.domain.entites.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PhotoService {
    Photo uploadPhoto(MultipartFile fiel);
    Optional<Resource> getPhotoAsResource(String id);
}
