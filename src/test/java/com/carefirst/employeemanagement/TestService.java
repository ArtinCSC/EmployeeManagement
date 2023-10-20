package com.carefirst.employeemanagement;

import com.carefirst.employeemanagement.model.Employee;
import org.springframework.test.web.servlet.ResultActions;

public interface TestService {

    ResultActions get(String path) throws Exception;

    ResultActions put(String path, Object o) throws Exception;

    ResultActions post(String path, Object o) throws Exception;

    <T> T get(String path, Class<T> clazz) throws Exception;

    <T> T put(String path, Object o, Class<T> clazz) throws Exception;

    <T> T post(String path, Object o, Class<T> clazz) throws Exception;

    ResultActions delete(String path) throws Exception;

    void createEmployees(int numberOfEmployee);

    Employee getEmployee();
}
