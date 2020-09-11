package com.dss.data.jpa.app.view.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
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

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final Bill bill = (Bill) model.get("bill");
		
		PdfPTable customerTable = new PdfPTable(1);
		customerTable.setSpacingAfter(20);
		customerTable.addCell("Customer Data");
		customerTable.addCell(bill.getCustomer().getName().concat(" ").concat(bill.getCustomer().getLastname()));
		customerTable.addCell(bill.getCustomer().getEmail());
		
		PdfPTable billTable = new PdfPTable(1);
		billTable.setSpacingAfter(20);
		billTable.addCell("Bill Data");
		billTable.addCell("Nro: ".toString().concat(bill.getId().toString()));
		billTable.addCell("Description: ".concat(bill.getDescription()));
		billTable.addCell("Date: ".concat(bill.getCreatedAt().toString()));
		
		document.add(customerTable);
		document.add(billTable);
		
		PdfPTable billItemTable = new PdfPTable(4);
		billItemTable.addCell("Description");
		billItemTable.addCell("Quantity");
		billItemTable.addCell("Price");
		billItemTable.addCell("Total");
		
		for(BillItem bi : bill.getItems()) {
			billItemTable.addCell(bi.getProduct().getDescription());
			billItemTable.addCell(bi.getQuantity().toString());
			billItemTable.addCell(bi.getProduct().getPrice().toString());
			billItemTable.addCell(bi.getSubtotal().toString());
		}
		
		PdfPCell cell = new PdfPCell(new Phrase("Total"));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		billItemTable.addCell(cell);
		billItemTable.addCell(bill.getTotal().toString());
		
		document.add(billItemTable);
	}

}
