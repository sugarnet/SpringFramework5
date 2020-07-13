package com.dss.di.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.dss.di.app.service.GenericService;
import com.dss.di.app.service.impl.MyOtherService;
import com.dss.di.app.service.impl.MyService;

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

}
