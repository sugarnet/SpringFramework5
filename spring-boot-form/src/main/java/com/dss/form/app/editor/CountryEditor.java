package com.dss.form.app.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dss.form.app.service.CountryService;

@Component
public class CountryEditor extends PropertyEditorSupport {
	
	@Autowired
	private CountryService countryService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(text);
			setValue(countryService.getById(id));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
