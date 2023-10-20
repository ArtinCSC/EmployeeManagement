package com.carefirst.employeemanagement.service;

import com.carefirst.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmployeeManagementService {

    List<Employee> getAllEmployees();

    Page<Employee> search(Specification<Employee> spec, Pageable page);

    Employee getEmployee(Long id);

    Boolean deleteEmployee(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

}
