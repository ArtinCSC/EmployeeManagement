package com.carefirst.employeemanagement;

import com.carefirst.employeemanagement.model.Employee;
import com.carefirst.employeemanagement.model.address.Address;
import com.carefirst.employeemanagement.model.address.StateType;
import com.carefirst.employeemanagement.repository.EmployeeManagementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@Component
public class TestServiceImpl implements TestService {

    @Autowired
    private ObjectMapper mapper;
    private MockMvc client;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private EmployeeManagementRepository employeeRepo;

    @Override
    public ResultActions get(String path) throws Exception {
        return this.client.perform(
                MockMvcRequestBuilders
                        .get(path)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Override
    public <T> T get(String path, Class<T> clazz) throws Exception {
        String json = this.client.perform(MockMvcRequestBuilders
                        .get(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return mapper.readValue(json, clazz);
    }

    @Override
    public ResultActions put(String path, Object o) throws Exception {
        return this.client.perform(
                MockMvcRequestBuilders
                        .put(path)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writer().writeValueAsString(o))
        );
    }

    @Override
    public <T> T put(String path, Object o, Class<T> clazz) throws Exception {
        String json = put(path, o)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        return mapper.readValue(json, clazz);
    }

    @Override
    public ResultActions post(String path, Object o) throws Exception {
        String json = this.mapper.writer().writeValueAsString(o);
        log.debug("Posting json: {}", json);
        return this.client.perform(
                MockMvcRequestBuilders
                        .post(path)
                        .characterEncoding("UTF-8")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Override
    public <T> T post(String path, Object o, Class<T> clazz) throws Exception {
        String json = post(path, o)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse().getContentAsString();
        return mapper.readValue(json, clazz);
    }

    @Override
    public ResultActions delete(String path) throws Exception {
        return this.client.perform(MockMvcRequestBuilders.delete(path));
    }

    @Override
    public void createEmployees(int numberOfEmployee) {
        while (numberOfEmployee > 0) {
            employeeRepo.save(getEmployee());
            numberOfEmployee--;
        }
    }

    @Override
    public Employee getEmployee() {
        Address address = new Address();
        address.setLineOne("Matthew Dr.");
        address.setState(StateType.MD);
        Employee employee = new Employee();
        employee.setFirstName("Artin");
        employee.setLastName("lastName");
        employee.setEmailAddress("email@gmail.com");
        employee.setEmployeeId("employeeId");
        employee.setAddress(address);
        return employee;
    }

    @PostConstruct
    public void setup() {
        this.client = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
