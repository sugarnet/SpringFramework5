package com.dss.spring.boot.webflux.app.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.dss.spring.boot.webflux.app.models.dao.ProductDAO;
import com.dss.spring.boot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired 
	private ProductDAO productDAO;
	
	@GetMapping({"", "/", "/list"})
	public String list(Model model) {
		Flux<Product> products = productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		});
		
		products.subscribe(p -> LOGGER.info(p.getName()));
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "list";
	}
	
	@GetMapping("/list-datadriver")
	public String listDataDriver(Model model) {
		Flux<Product> products = productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		}).delayElements(Duration.ofSeconds(1));
		
		products.subscribe(p -> LOGGER.info(p.getName()));
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 1));
		model.addAttribute("title", "Product List");
		
		return "list";
	}
	
	@GetMapping("/list-full")
	public String listFull(Model model) {
		Flux<Product> products = productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		}).repeat(5000);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "list";
	}
	
	@GetMapping("/list-chunk")
	public String listChunk(Model model) {
		Flux<Product> products = productDAO.findAll().map(p -> {
			p.setName(p.getName().toUpperCase());
			return p;
		}).repeat(5000);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "Product List");
		
		return "list-chunk";
	}
}
