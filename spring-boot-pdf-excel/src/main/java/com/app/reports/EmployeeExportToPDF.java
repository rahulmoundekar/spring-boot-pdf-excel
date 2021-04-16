package com.app.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.app.entity.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class EmployeeExportToPDF {

	List<Employee> listOfEmployees = Collections.emptyList();

	public EmployeeExportToPDF(List<Employee> listOfEmployees) {
		this.listOfEmployees = listOfEmployees;
	}

	public void exportPdf(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(20);
		font.setColor(Color.ORANGE);

		Paragraph title = new Paragraph("List of Employees", font);

		document.add(title);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] { 1.5f, 4f, 4f, 4f });

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.YELLOW);
		cell.setPadding(4);

		cell.setPhrase(new Phrase("ID"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Address"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mobile No."));
		table.addCell(cell);

		for (Employee employee : listOfEmployees) {
			table.addCell(String.valueOf(employee.getId()));
			table.addCell(employee.getName());
			table.addCell(employee.getAddress());
			table.addCell(employee.getMobile());
		}

		document.add(table);
		document.close();
	}

}
