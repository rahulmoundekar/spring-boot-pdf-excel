package com.app.reports;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.entity.Employee;

public class EmployeeExportToExcel {
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	List<Employee> listOfEmployees = Collections.emptyList();

	public EmployeeExportToExcel(List<Employee> listOfEmployees) {
		this.listOfEmployees = listOfEmployees;
		workbook = new XSSFWorkbook();
	}

	private void excelHeader() {
		sheet = workbook.createSheet("employees");
		Row row = sheet.createRow(0);

		createCells(row, 0, "Id");
		createCells(row, 1, "Name");
		createCells(row, 2, "Address");
		createCells(row, 3, "Mobile No.");

	}

	private void createCells(Row row, int colCount, Object value) {
		Cell cell = row.createCell(colCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else {
			cell.setCellValue((String) value);
		}
	}

	public void exportToExcel(HttpServletResponse response) throws IOException {
		excelHeader();
		int rowCount = 1;

		for (Employee employee : listOfEmployees) {
			Row row = sheet.createRow(rowCount++);
			int colCount = 0;
			createCells(row, colCount++, employee.getId());
			createCells(row, colCount++, employee.getName());
			createCells(row, colCount++, employee.getAddress());
			createCells(row, colCount++, employee.getMobile());
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
