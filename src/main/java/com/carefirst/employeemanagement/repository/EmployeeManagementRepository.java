package com.carefirst.employeemanagement.repository;

import com.carefirst.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeManagementRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAll(Specification<Employee> spec, Pageable page);
}
