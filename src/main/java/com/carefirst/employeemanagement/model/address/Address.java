package com.carefirst.employeemanagement.model.address;

import com.carefirst.employeemanagement.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = -437803647787497254L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String lineOne;
    private String lineTwo;
    private String city;
    @Enumerated(EnumType.STRING)
    private StateType state;
    private String zipcode;
    private String country;
    private String county;
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "address")
    private Employee employee;

}
