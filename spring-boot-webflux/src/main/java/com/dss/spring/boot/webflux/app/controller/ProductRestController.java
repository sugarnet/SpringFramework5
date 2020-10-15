package com.dss.spring.boot.webflux.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dss.spring.boot.webflux.app.models.dao.ProductDAO;
import com.dss.spring.boot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductDAO productDAO;

	@GetMapping
	public Flux<Product> findAll() {
		return productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		});
	}

	@GetMapping("/{id}")
	public Mono<Product> findById(@PathVariable String id) {
		Flux<Product> products = productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		});

		return products.filter(p -> p.getId().equals(id)).next();
	}
}
