package com.dss.di.app.model.service.impl;

import org.springframework.stereotype.Service;

import com.dss.di.app.model.service.GenericService;

// @Service("myOtherService")
public class MyOtherService implements GenericService {

	@Override
	public String operation() {
		// TODO Auto-generated method stub
		return "Breaking something from MyOtherService...";
	}

}
