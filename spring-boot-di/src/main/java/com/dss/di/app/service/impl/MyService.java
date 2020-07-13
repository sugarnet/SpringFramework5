package com.dss.di.app.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.dss.di.app.service.GenericService;

// @Service("myService")
// @Primary // we use primary if we don't user Qualifier for indicate what service use
public class MyService implements GenericService {

	@Override
	public String operation() {
		return "Doing something...";
	}
}
