package com.example.restaurant_review_api_springboot.repositories;

import com.example.restaurant_review_api_springboot.domain.entites.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RestaurantRepository extends ElasticsearchRepository<Restaurant,String> {
}
