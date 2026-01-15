package com.example.restaurant_review_api_springboot.controllers;


import com.example.restaurant_review_api_springboot.domain.dtos.PhotoDto;
import com.example.restaurant_review_api_springboot.mapper.PhotoMapper;
import com.example.restaurant_review_api_springboot.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @Autowired
    public PhotoController(PhotoService photoService, PhotoMapper photoMapper) {
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    @PostMapping
    public PhotoDto uploadPhoto(
            @RequestParam("file") MultipartFile file
    ) {
        var photo = photoService.uploadPhoto(file);
        return  photoMapper.toDto(photo);
    }


    @GetMapping(path = "/{id:.+}")
    public ResponseEntity<Resource> getPhoto(
            @PathVariable String id
    ) {
        return   photoService.getPhotoAsResource(id)
                .map( photo ->
                        ResponseEntity.ok()
                                .contentType(
                                        MediaTypeFactory.getMediaType(photo)
                                                .orElse(MediaType.APPLICATION_OCTET_STREAM)
                                )
                                .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                                .body(photo)
                ).orElse(ResponseEntity.notFound().build());
    }


}
