package com.carefirst.employeemanagement.service;

import com.carefirst.employeemanagement.TestService;
import com.carefirst.employeemanagement.api.exception.NotFoundException;
import com.carefirst.employeemanagement.model.Employee;
import com.carefirst.employeemanagement.repository.EmployeeManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class EmployeeManagementServiceTest {

    @Autowired
    private EmployeeManagementService employeeService;
    @Autowired
    private TestService service;
    @Autowired
    private EmployeeManagementRepository employeeRepo;

    @AfterEach
    void tearDown() {
        employeeRepo.deleteAll();
    }

    @Test
    void givenNoEmployWhenGetEmployeeThenNoSuchElementException() {

        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> {
            employeeService.getEmployee(1L);
        });
        assertEquals("No value present", noSuchElementException.getMessage());
    }

    @Test
    void givenNoEmployeeWhenUpdateThenNotFoundException() {
        Employee employee = service.getEmployee();
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            employeeService.updateEmployee(123L, employee);
        });
        assertEquals("invalid Employee Id. Employee Not Found!", notFoundException.getMessage());
    }

    @Test
    void givenNewEmployeeWhenCreateThenCreated() {
        // employee object without database id
        Employee employee = service.getEmployee();
        assertNull(employee.getId());

        employeeService.createEmployee(employee);
        assertNotNull(employee.getId());
    }

}
