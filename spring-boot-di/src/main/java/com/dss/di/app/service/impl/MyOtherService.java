package com.dss.di.app.service.impl;

import org.springframework.stereotype.Service;

import com.dss.di.app.service.GenericService;

// @Service("myOtherService")
public class MyOtherService implements GenericService {

	@Override
	public String operation() {
		// TODO Auto-generated method stub
		return "Breaking something from MyOtherService...";
	}

}
