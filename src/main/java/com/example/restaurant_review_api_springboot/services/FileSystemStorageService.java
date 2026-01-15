package com.example.restaurant_review_api_springboot.services;

import com.example.restaurant_review_api_springboot.exceptions.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storageLocation);

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw  new StorageException("couldnot init storage location");
        }
    }

    @Override
    public String store(MultipartFile file, String filename) {

        try {
            if(file.isEmpty()) {
                throw new StorageException("canot save empty file");
            }
            var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var fileName = filename + "." + extension;

            var destinationFile = rootLocation
                    .resolve(Paths.get(fileName))
                    .normalize()
                    .toAbsolutePath();


            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw  new StorageException("canot store file outside specified directory");
            }

            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return fileName;
        }catch (Exception ex) {
            log.error("Failed to store file", ex);
            throw new StorageException("Failed to store file", ex);
        }
    }

    @Override
    public Optional<Resource> loadAsResource(String id) {

       try {
           Path file =  rootLocation.resolve(id);
           Resource resource =   new UrlResource(file.toUri());

           if (resource.exists() || resource.isReadable()) {
               return  Optional.of(resource);
           }else {
                return  Optional.empty();
           }
       } catch (Exception ex) {
            log.warn("no read file can do " + id,ex);
            return Optional.empty();
       }
    }
}
