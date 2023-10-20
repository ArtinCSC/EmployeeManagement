package com.carefirst.employeemanagement.api;

import com.carefirst.employeemanagement.model.Employee;
import com.carefirst.employeemanagement.model.EmployeeCriteria;
import com.carefirst.employeemanagement.service.EmployeeManagementService;
import com.carefirst.employeemanagement.service.core.PageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("employees")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService service;

    /**
     * warning: this API might return a large payload
     *
     * @return All the employees in DB
     */
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    /**
     * @param id               Long employee DB id (Table primary Key)
     * @param firstName        String employee first name
     * @param lastName         String employee last name
     * @param emailAddress     String employee email address
     * @param phone            String employee phone number
     * @param jobTitle         String employee Job Title
     * @param department       String employee department
     * @param employeeId       String employee ID
     * @param reportingManager String employee reporting manager
     * @param dateOfBirth      Date employee date of birth
     * @param pageNumber       number page number (Default 0)
     * @param pageSize         number page size (default 20)
     * @param sort             list of String of sorting and direction in this format example (id:DESC)
     * @return sorted pageable of Employee object based on given page number abd page size
     */
    @GetMapping()
    public Page<Employee> search(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "emailAddress", required = false) String emailAddress,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "jobTitle", required = false) String jobTitle,
            @RequestParam(name = "department", required = false) String department,
            @RequestParam(name = "employeeId", required = false) String employeeId,
            @RequestParam(name = "reportingManager", required = false) String reportingManager,
            @RequestParam(name = "dateOfBirth", required = false) LocalDate dateOfBirth,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "id:DESC") List<String> sort
    ) {

        Pageable page = new PageBuilder()
                .sort(sort)
                .page(pageNumber)
                .size(pageSize)
                .build();

        EmployeeCriteria criteria = new EmployeeCriteria()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .phone(phone)
                .jobTitle(jobTitle)
                .department(department)
                .employeeId(employeeId)
                .reportingManager(reportingManager)
                .dateOfBirth(dateOfBirth);

        return service.search(criteria, page);
    }

    /**
     * @param id Long employee DB id (Table primary Key)
     * @return specific employee
     */
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return service.getEmployee(id);
    }

    /**
     * @param id Long employee DB id (Table primary Key)
     * @return true if employee was found and deleted, false otherwise
     */
    @DeleteMapping("/{id}")
    public Boolean deleteEmployee(@PathVariable("id") Long id) {
        return service.deleteEmployee(id);
    }

    /**
     * @param employee employee object to be added to table
     * @return employee with a new id (Table id/Primary key)
     */
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.createEmployee(employee);
    }

    /**
     * @param id       Long employee DB id (Table primary Key)
     * @param employee employee object to be updated
     * @return return updated employee, if the id/employee does not exist, it creates a new employee
     */
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }
}
