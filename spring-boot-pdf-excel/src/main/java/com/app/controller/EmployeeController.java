package com.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.entity.Employee;
import com.app.repo.EmployeeRepo;
import com.app.reports.EmployeeExportToExcel;
import com.app.reports.EmployeeExportToPDF;
import com.lowagie.text.DocumentException;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepo employeeRepo;

	@GetMapping(value = "/")
	public String viewHome() {
		return "index";
	}

	@GetMapping(value = "employeePdf")
	public void listOfEmployee(HttpServletResponse response) {

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=employee.pdf");

		List<Employee> listOfEmployees = employeeRepo.findAll();

		EmployeeExportToPDF exportToPDF = new EmployeeExportToPDF(listOfEmployees);
		try {
			exportToPDF.exportPdf(response);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "employeeExcel")
	public void listOfEmployeeExcel(HttpServletResponse response) {

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=employee.xlsx");

		List<Employee> listOfEmployees = employeeRepo.findAll();

		EmployeeExportToExcel exportToExcel = new EmployeeExportToExcel(listOfEmployees);
		try {
			exportToExcel.exportToExcel(response);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

}
