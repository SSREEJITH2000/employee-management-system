package com.employee.managementsystem.controller;

import com.employee.managementsystem.repository.EmployeeRepository;
import com.employee.managementsystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;
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
    void getEmployeesByDepartment() throws Exception {
        String department = "Engineering";
        when(employeeService.getEmployeesByDepartment(department))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/get")
                .param("department", department);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
