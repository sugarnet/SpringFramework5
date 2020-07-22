package com.dss.form.app.domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
// import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
// import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// import org.springframework.format.annotation.DateTimeFormat;

import com.dss.form.app.validator.IdentifierRegex;
import com.dss.form.app.validator.ItIsRequired;

public class User {

	// @Pattern(regexp = "[//d]{2}[.][//d]{3}[.][//d]{3}[-][A-Z]{3}")
	@IdentifierRegex
	private String identifier;

	// @NotEmpty(message = "Name can't be empty!")
	private String name;

	@ItIsRequired
	private String surname;

	@NotEmpty
	@Size(min = 3, max = 8)
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	@Email
	private String email;

	@NotNull // we use it when the field has a referenced type, if the field it is primitive
				// we don't use it
	@Min(10)
	@Max(10000)
	private Integer account;

	@NotNull
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	// @Future
	private Date birthDate;

	@Valid // we use when the field class has validations too
	private Country country;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
