package com.employee.managementsystem.controller;

import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.repository.EmployeeRepository;
import com.employee.managementsystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void addEmployeeTest() throws Exception {
        String json = "{\"name\":\"First name\",\"department\":\"First dept\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
    @Test
    void getEmployeeByIdTest() throws Exception{
        Long employeeId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/" + employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void searchEmployeeByDepartmentTest() throws Exception{
        EmployeeResponse response = new EmployeeResponse();
        EmployeeResponse response1 = new EmployeeResponse();
        when(employeeService.searchEmployeeByDepartment("IT")).thenReturn(Arrays.asList(response,response1));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/search")
                        .param("department", "IT"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(employeeService,times(1)).searchEmployeeByDepartment("IT");
    }
}



