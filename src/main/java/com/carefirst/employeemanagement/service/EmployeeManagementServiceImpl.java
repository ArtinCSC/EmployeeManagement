package com.carefirst.employeemanagement.service;

import com.carefirst.employeemanagement.api.exception.NotFoundException;
import com.carefirst.employeemanagement.model.Employee;
import com.carefirst.employeemanagement.repository.EmployeeManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    @Autowired
    private EmployeeManagementRepository repo;

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Page<Employee> search(Specification<Employee> spec, Pageable page) {
        return repo.findAll(spec, page);
    }

    @Override
    public Employee getEmployee(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        Optional<Employee> employee = repo.findById(id);
        if (employee.isPresent()) {
            employee.ifPresent(value -> repo.delete(value));
            return true;
        }
        return false;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existingEmployee = repo.findById(id);
        if (existingEmployee.isPresent()) {
            return repo.save(employee);
        } else {
            throw new NotFoundException("invalid Employee Id. Employee Not Found!");
        }
    }
}
