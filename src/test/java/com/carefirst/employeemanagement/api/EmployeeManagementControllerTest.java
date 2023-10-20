package com.carefirst.employeemanagement.api;

import com.carefirst.employeemanagement.TestService;
import com.carefirst.employeemanagement.api.exception.NotFoundException;
import com.carefirst.employeemanagement.model.Employee;
import com.carefirst.employeemanagement.repository.EmployeeManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class EmployeeManagementControllerTest {

    @Autowired
    private EmployeeManagementRepository employeeRepo;
    @Autowired
    private TestService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        employeeRepo.deleteAll();
    }

    @Test
    void givenEmployeesWhenGetAllEmployeesThenReturnValues() throws Exception {
        service.createEmployees(4);

        service.get("/employees/all")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address.lineOne", Matchers.equalTo("Matthew Dr.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].firstName", Matchers.equalTo("Artin")));

    }

    @Test
    void givenEmployeesWhenSearchThenReturnCorrectResult() throws Exception {
        service.createEmployees(4);

        service.get("/employees")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(4)));

        service.get("/employees?lastName=lastName")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName", Matchers.equalTo("Artin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].emailAddress", Matchers.equalTo("email@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].address.lineOne", Matchers.equalTo("Matthew Dr.")));
    }

    @Test
    void givenEmployeeWhenGetSpecificEmployeeThenReturnCorrectResult() throws Exception {
        service.createEmployees(1);
        Long id = getEmployeeId();
        Employee employee = service.get("/employees/" + id, Employee.class);
        assertNotNull(employee);
        assertEquals(employee.getId(), id);
        assertEquals("Artin", employee.getFirstName());
    }

    @Test
    void givenNoEmployWhenGetEmployeeThenExceptionHandlerHandleNoSuchElementException() throws Exception {

        service.get("/employees/1")
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.equalTo("NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("No value present")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.equalTo("NOT_FOUND")));

    }

    @Test
    void givenNoEmployWhenUpdateEmployeeThenExceptionHandlerHandleNotFoundExceptionException() throws Exception {

        service.put("/employees/1", new Employee())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.equalTo("NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.equalTo("Not found")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("invalid Employee Id. Employee Not Found!")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.equalTo("NOT_FOUND")));

    }

    @Test
    void givenEmployeeWhenDeleteThenDelete() throws Exception {
        service.createEmployees(1);
        Long id = getEmployeeId();

        service.delete("/employees/" + id)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.equalTo(true)));
    }

    @Test
    void givenNewEmployeeWhenCreateThenCreate() throws Exception {
        Employee employee = service.getEmployee();

        service.post("/employees", employee)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void givenNewEmployeeWhenCreateThenCreateAndReturn() throws Exception {
        Employee employee = service.getEmployee();

        Employee returnedEmployee = service.post("/employees", employee, Employee.class);

        assertNotNull(returnedEmployee);
        assertEquals("Artin", returnedEmployee.getFirstName());
        assertEquals("Matthew Dr.", returnedEmployee.getAddress().getLineOne());
    }

    @Test
    void updateEmployee() throws Exception {
        // existing employee
        service.createEmployees(1);

        Employee employee = service.getEmployee();
        employee.setFirstName("NewName");
        employee.getAddress().setLineOne("New Address");
        Long id = getEmployeeId();
        service.put("/employees/" + id, employee)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.equalTo("NewName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.lineOne", Matchers.equalTo("New Address")));
    }

    private Long getEmployeeId() {
        List<Employee> employeeList = employeeRepo.findAll();
        if (!employeeList.isEmpty()) {
            return employeeList.getFirst().getId();
        }
        throw new NotFoundException("Employee Not Found!");
    }

}