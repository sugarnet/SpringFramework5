package com.dss.data.jpa.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.dss.data.jpa.app.entity.Customer;

@Component("modules/customer/list.xml")
public class CustomerListXmlView extends MarshallingView {

	@Autowired
	public CustomerListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		model.remove("title");
		model.remove("page");

		Page<Customer> customers = (Page<Customer>) model.get("customers");

		model.remove("customers");

		model.put("customerList", new CustomerList(customers.getContent()));

		super.renderMergedOutputModel(model, request, response);
	}

}
