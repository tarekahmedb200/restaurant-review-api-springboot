package com.example.restaurant_review_api_springboot.mapper;

import com.example.restaurant_review_api_springboot.domain.dtos.PhotoDto;
import com.example.restaurant_review_api_springboot.domain.entites.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);


}
