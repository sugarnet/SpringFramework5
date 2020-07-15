package com.dss.di.app.model.domain;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
// scopes
@RequestScope // for each request the container generates a new instance
// @SessionScope // the bean has duration of browser's session
// @ApplicationScope // we can say that it is the same a SingletonScope
public class Bill {

	@Value("${bill.description}")
	private String description;

	@Autowired
	private Client client;

	@Autowired
	@Qualifier("itemsBill")
	private List<ItemBill> items;
	
	@PostConstruct
	public void init() {
		client.setName(client.getName().concat(" ").concat("David"));
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println(client.getName().concat(" bill was destroyed"));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<ItemBill> getItems() {
		return items;
	}

	public void setItems(List<ItemBill> items) {
		this.items = items;
	}

}
