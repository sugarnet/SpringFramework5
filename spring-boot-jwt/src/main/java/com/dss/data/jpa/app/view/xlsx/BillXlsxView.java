package com.dss.data.jpa.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.dss.data.jpa.app.entity.Bill;
import com.dss.data.jpa.app.entity.BillItem;

@Component("modules/bill/details.xlsx")
public class BillXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + messageSourceAccessor.getMessage("text.bill.details.xlsx.sheet.name") + ".xlsx\"");

		final Bill bill = (Bill) model.get("bill");
		
		Sheet sheet = workbook.createSheet(messageSourceAccessor.getMessage("text.bill.details.xlsx.sheet.name"));
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(messageSourceAccessor.getMessage("text.customer.details.customer.data"));
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(bill.getCustomer().getName().concat(" ").concat(bill.getCustomer().getLastname()));
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(bill.getCustomer().getEmail());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		
		sheet.createRow(4).createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.details.bill.data"));
		sheet.createRow(5).createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.details.nro").concat(": ").concat(bill.getId().toString()));
		sheet.createRow(6).createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.details.description").concat(": ").concat(bill.getDescription()));
		sheet.createRow(7).createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.details.date").concat(": ").concat(bill.getCreatedAt().toString()));
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.details.bill.item.description"));
		header.createCell(1).setCellValue(messageSourceAccessor.getMessage("text.bill.details.bill.item.quantity"));
		header.createCell(2).setCellValue(messageSourceAccessor.getMessage("text.bill.details.bill.item.price"));
		header.createCell(3).setCellValue(messageSourceAccessor.getMessage("text.bill.details.bill.item.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum = 10;
		for(BillItem bi : bill.getItems()) {
			Row item = sheet.createRow(rownum++);
			
			cell = item.createCell(0);
			cell.setCellValue(bi.getProduct().getDescription());
			cell.setCellStyle(tbodyStyle);
			
			cell = item.createCell(1);
			cell.setCellValue(bi.getQuantity());
			cell.setCellStyle(tbodyStyle);
			
			cell = item.createCell(2);
			cell.setCellValue(bi.getProduct().getPrice());
			cell.setCellStyle(tbodyStyle);
			
			cell = item.createCell(3);
			cell.setCellValue(bi.getSubtotal());
			cell.setCellStyle(tbodyStyle);
		}
		
		Row total = sheet.createRow(rownum);
		cell = total.createCell(2); 
		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.details.total"));
		cell.setCellStyle(tbodyStyle);
		
		cell = total.createCell(3);
		cell.setCellValue(bill.getTotal());
		cell.setCellStyle(tbodyStyle);
	}

}
