package com.dss.spring.boot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.dss.spring.boot.webflux.app.models.dao.ProductDAO;
import com.dss.spring.boot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("product").subscribe();

		Flux.just(new Product("Product 1", 23.3), new Product("Product 2", 24.3), new Product("Product 3", 25.3),
						new Product("Product 4", 26.3), new Product("Product 5", 27.3), new Product("Product 6", 28.3),
						new Product("Product 7", 29.3), new Product("Product 8", 30.3))
				.flatMap(p -> {
					p.setCreatedAt(new Date());
					return productDAO.save(p);
				})
				.subscribe(p -> LOGGER.info("Inserted: " + p.getId() + " " + p.getName()));
	}

}
