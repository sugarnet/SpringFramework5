package com.dss.spring.boot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.dss.spring.boot.webflux.app.models.documents.Product;

public interface ProductDAO extends ReactiveMongoRepository<Product, String> {

}
