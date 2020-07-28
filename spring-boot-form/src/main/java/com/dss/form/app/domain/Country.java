package com.dss.form.app.domain;

// import javax.validation.constraints.NotNull;

public class Country {

	// @NotNull
	private Integer id;
	private String code;
	private String name;

	public Country() {
	}

	public Country(Integer id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// we implement toString for match specific initial value in the form (country field)
		return id.toString();
	}
	
	

}
