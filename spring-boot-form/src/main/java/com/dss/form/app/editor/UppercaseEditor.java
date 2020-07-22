package com.dss.form.app.editor;

import java.beans.PropertyEditorSupport;

public class UppercaseEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text.toUpperCase().trim());
	}

}
