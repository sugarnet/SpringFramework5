package com.dss.data.jpa.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dss.data.jpa.app.entity.Customer;

@XmlRootElement(name = "customers")
public class CustomerList {

	@XmlElement(name = "customer")
	private List<Customer> customers;

	public CustomerList() {
	}

	public CustomerList(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
