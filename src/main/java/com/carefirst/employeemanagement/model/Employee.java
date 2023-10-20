package com.carefirst.employeemanagement.model;

import com.carefirst.employeemanagement.model.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = -437803647787497250L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;
    private LocalDate dateOfBirth;
    private String jobTitle;
    private String department;
    private LocalDate startDate;
    private String employeeId;
    private String reportingManager;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address = new Address();

}
