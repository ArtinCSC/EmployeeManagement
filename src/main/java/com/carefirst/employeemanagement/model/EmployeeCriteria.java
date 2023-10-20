package com.carefirst.employeemanagement.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeCriteria implements Specification<Employee> {

    private final List<Predicate> predicates = new ArrayList<>();
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;
    private LocalDate dateOfBirth;
    private String jobTitle;
    private String department;
    private String employeeId;
    private String reportingManager;

    /**
     * Creates a WHERE clause for a query of the referenced entity in form of a Predicate for the given Root and CriteriaQuery.
     *
     * @param employeeRoot must not be {@literal null}.
     * @param query        must not be {@literal null}.
     * @param cb           must not be {@literal null}.
     * @return a Predicate, may be null.
     */
    @Override
    public Predicate toPredicate(Root<Employee> employeeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (this.id != null) {
            this.predicates.add(cb.equal(employeeRoot.get("id"), this.id));
        }
        if (StringUtils.isNotBlank(this.firstName)) {
            this.predicates.add(cb.like(employeeRoot.get("firstName"), this.firstName));
        }
        if (StringUtils.isNotBlank(this.lastName)) {
            this.predicates.add(cb.like(employeeRoot.get("lastName"), this.lastName));
        }
        if (StringUtils.isNotBlank(this.emailAddress)) {
            this.predicates.add(cb.like(employeeRoot.get("emailAddress"), this.emailAddress));
        }
        if (StringUtils.isNotBlank(this.phone)) {
            this.predicates.add(cb.equal(employeeRoot.get("phone"), this.phone));
        }
        if (StringUtils.isNotBlank(this.jobTitle)) {
            this.predicates.add(cb.like(employeeRoot.get("jobTitle"), this.jobTitle));
        }
        if (StringUtils.isNotBlank(this.department)) {
            this.predicates.add(cb.like(employeeRoot.get("department"), this.department));
        }
        if (StringUtils.isNotBlank(this.employeeId)) {
            this.predicates.add(cb.equal(employeeRoot.get("employeeId"), this.employeeId));
        }
        if (StringUtils.isNotBlank(this.reportingManager)) {
            this.predicates.add(cb.like(employeeRoot.get("reportingManager"), this.reportingManager));
        }
        if (this.dateOfBirth != null) {
            this.predicates.add(cb.equal(employeeRoot.get("dateOfBirth"), this.dateOfBirth));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    public EmployeeCriteria id(Long id) {
        this.id = id;
        return this;
    }

    public EmployeeCriteria firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeCriteria lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeCriteria emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public EmployeeCriteria phone(String phone) {
        this.phone = phone;
        return this;
    }

    public EmployeeCriteria dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public EmployeeCriteria jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public EmployeeCriteria department(String department) {
        this.department = department;
        return this;
    }

    public EmployeeCriteria employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public EmployeeCriteria reportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
        return this;
    }
}
