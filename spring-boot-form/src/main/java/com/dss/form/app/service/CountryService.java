package com.dss.form.app.service;

import java.util.List;

import com.dss.form.app.domain.Country;

public interface CountryService {

	List<Country> getAll();
	Country getById(Integer id);
}
