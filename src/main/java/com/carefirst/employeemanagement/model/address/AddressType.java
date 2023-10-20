package com.carefirst.employeemanagement.model.address;

public enum AddressType {

    PERMANENT("Permanent"),
    MAILING("Mailing"),
    TEMP("Temporary");

    private final String description;

    AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
