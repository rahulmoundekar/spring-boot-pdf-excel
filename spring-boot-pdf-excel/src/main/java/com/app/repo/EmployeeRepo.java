package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
