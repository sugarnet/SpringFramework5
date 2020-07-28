package com.dss.form.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.dss.form.app.domain.Country;
import com.dss.form.app.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	
	private List<Country> countries;
	
	@PostConstruct
	private void init() {
		countries = new ArrayList<>();
		countries.addAll(Arrays.asList(new Country(1, "AR", "Argentina"), new Country(2, "BO", "Bolivia"),
				new Country(3, "BR", "Brasil"), new Country(4, "CL", "Chile"), new Country(5, "PA", "Paraguay"),
				new Country(6, "UR", "Uruguay")));
	}
	
	@Override
	public List<Country> getAll() {
		return countries;
	}

	@Override
	public Country getById(Integer id) {
		return countries.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
	}

}
