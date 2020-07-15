package com.dss.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.dss.di.app.model.domain.ItemBill;
import com.dss.di.app.model.domain.Product;
import com.dss.di.app.model.service.GenericService;
import com.dss.di.app.model.service.impl.MyOtherService;
import com.dss.di.app.model.service.impl.MyService;

@Configuration
public class AppConfig {

	@Bean("myService")
	public GenericService myServiceRegister() {
		return new MyService();
	}
	
	@Bean("myOtherService")
	@Primary
	public GenericService myOtherServiceRegister() {
		return new MyOtherService();
	}
	
	@Bean("itemsBill")
	public List<ItemBill> itemsBillRegister() {
		Product p1 = new Product("Lentejas", 10);
		Product p2 = new Product("Porotos", 20);
		
		ItemBill i1 = new ItemBill(p1, 2);
		ItemBill i2 = new ItemBill(p2, 4);
		
		return Arrays.asList(i1, i2);
	}
	
	@Bean("otherItemsBill")
	public List<ItemBill> otherItemsBillRegister() {
		Product p1 = new Product("Choclo", 10);
		Product p2 = new Product("Azúcar", 15);
		Product p3 = new Product("Vino", 30);
		Product p4 = new Product("Yerba", 20);
		
		ItemBill i1 = new ItemBill(p1, 2);
		ItemBill i2 = new ItemBill(p2, 3);
		ItemBill i3 = new ItemBill(p3, 4);
		ItemBill i4 = new ItemBill(p4, 1);
		
		return Arrays.asList(i1, i2, i3, i4);
	}

}
