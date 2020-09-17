package com.dss.data.jpa.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.dss.data.jpa.app.entity.Customer;

@Component("modules/customer/list")
public class CustomersCsvView extends AbstractView {
	
	public CustomersCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + messageSourceAccessor.getMessage("text.customer.list.title") + ".csv\"");
		response.setContentType(getContentType());
		
		Page<Customer> customers = (Page<Customer>) model.get("customers");
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String header[] = {"id", "name", "lastname", "email", "createdAt"};
		beanWriter.writeHeader(header);
		
		for (Customer customer : customers) {
			beanWriter.write(customer, header);
		}
		
		beanWriter.close();
		
	}

}
