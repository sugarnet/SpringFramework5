package com.dss.data.jpa.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.dss.data.jpa.app.entity.Bill;
import com.dss.data.jpa.app.entity.BillItem;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("modules/bill/details")
public class BillPdfView extends AbstractPdfView {

	/*
	 * https://itextpdf.com/es/resources/api-documentation
	 */
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired 
	private LocaleResolver localeResolver;
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final Bill bill = (Bill) model.get("bill");
		
		PdfPTable customerTable = new PdfPTable(1);
		customerTable.setSpacingAfter(20);
		
		Locale locale = localeResolver.resolveLocale(request);
		
		// using MessageSourceAccesor is better. Because this object uses localeResolver and messageSource
		MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();
		
		
		PdfPCell cell = new PdfPCell(new Phrase(messageSource.getMessage("text.customer.details.customer.data", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		customerTable.addCell(cell);
		customerTable.addCell(bill.getCustomer().getName().concat(" ").concat(bill.getCustomer().getLastname()));
		customerTable.addCell(bill.getCustomer().getEmail());
		
		PdfPTable billTable = new PdfPTable(1);
		billTable.setSpacingAfter(20);
		
		new PdfPCell(new Phrase(messageSource.getMessage("text.bill.details.bill.data", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		billTable.addCell(cell);
		billTable.addCell(messageSourceAccessor.getMessage("text.bill.details.nro").concat(": ").concat(bill.getId().toString()));
		billTable.addCell(messageSourceAccessor.getMessage("text.bill.details.description").concat(": ").concat(bill.getDescription()));
		billTable.addCell(messageSourceAccessor.getMessage("text.bill.details.date").concat(": ").concat(bill.getCreatedAt().toString()));
		
		document.add(customerTable);
		document.add(billTable);
		
		PdfPTable billItemTable = new PdfPTable(4);
		billItemTable.setWidths(new float[] {3.5f, 1, 1, 1});
		billItemTable.addCell(messageSourceAccessor.getMessage("text.bill.details.bill.item.description"));
		billItemTable.addCell(messageSourceAccessor.getMessage("text.bill.details.bill.item.quantity"));
		billItemTable.addCell(messageSourceAccessor.getMessage("text.bill.details.bill.item.price"));
		billItemTable.addCell(messageSourceAccessor.getMessage("text.bill.details.bill.item.total"));
		
		for(BillItem bi : bill.getItems()) {
			billItemTable.addCell(bi.getProduct().getDescription());
			
			cell = new PdfPCell(new Phrase(bi.getQuantity().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			billItemTable.addCell(cell);
			billItemTable.addCell(bi.getProduct().getPrice().toString());
			billItemTable.addCell(bi.getSubtotal().toString());
		}
		
		cell = new PdfPCell(new Phrase(messageSourceAccessor.getMessage("text.bill.details.total")));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		billItemTable.addCell(cell);
		billItemTable.addCell(bill.getTotal().toString());
		
		document.add(billItemTable);
	}

}
