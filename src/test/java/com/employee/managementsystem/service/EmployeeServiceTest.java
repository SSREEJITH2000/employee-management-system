package com.employee.managementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employee.managementsystem.contract.request.EmployeeRequest;
import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.exception.EmployeeNotFoundException;
import com.employee.managementsystem.model.Employee;
import com.employee.managementsystem.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private EmployeeService employeeService = new EmployeeService(null, null);

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        employeeService = new EmployeeService(employeeRepository, modelMapper);
    }

    @Test
    void addEmployeeTest() {
        Employee employee = new Employee(1L,"sreejith","sree34@gmail.com","maths");
        EmployeeRequest request = new EmployeeRequest("sreejith","sree34@gmail.com","maths");
        EmployeeResponse response = new EmployeeResponse(1L,"sreejith","sree34@gmail.com","maths");
        when(modelMapper.map(request, Employee.class)).thenReturn(employee);
        when((employeeRepository.save(any(Employee.class)))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(response);
        EmployeeResponse result = employeeService.addEmployee(request);
        assertEquals(result, response);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getEmployeeByIdTest() {
        long id = 1L;
        Employee employee = new Employee(1L,"sreejith","sree34@gmail.com","maths");
        EmployeeResponse response = new EmployeeResponse(1L,"sreejith","sree34@gmail.com","maths");
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(response);
        EmployeeResponse result = employeeService.getEmployeeById(id);
        assertEquals(result, response);
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetEmployeesByDepartment() {
        String department = "Engineering";
        Employee employee = new Employee(1L,"sreejith","sree34@gmail.com","maths");
        EmployeeResponse employeeResponse = new EmployeeResponse(1L,"sreejith","sree34@gmail.com","maths");
        when(employeeRepository.findByDepartment(department)).thenReturn(Arrays.asList(employee));
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(employeeResponse);

        List<EmployeeResponse> result = employeeService.getEmployeesByDepartment(department);

        assertEquals(1, result.size());
        assertEquals(employeeResponse, result.get(0));
        verify(employeeRepository, times(1)).findByDepartment(department);
        verify(modelMapper, times(1)).map(employee, EmployeeResponse.class);
    }

    @Test
    public void testGetEmployeesByDepartment_Empty() {
        String department = "Engineering";
        when(employeeRepository.findByDepartment(department)).thenReturn(Arrays.asList());
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeesByDepartment(department);
        });
    }
}
