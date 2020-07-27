package com.dss.form.app.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dss.form.app.service.RoleService;

@Component
public class RoleEditor extends PropertyEditorSupport {
	
	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(text);
			setValue(roleService.getById(id));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
