package com.employee.managementsystem.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.employee.managementsystem.repository.EmployeeRepository;
import com.employee.managementsystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired private EmployeeService employeeService;
    @Autowired private EmployeeController employeeController;
    @Autowired private MockMvc mockMvc;
    @Autowired private EmployeeRepository employeeRepository;

    @Test
    void addEmployeeTest() throws Exception {
        String json = "{\"name\":\"First name\",\"department\":\"First dept\"}";

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/employee/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Long employeeId = 1L;
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/employee/" + employeeId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
