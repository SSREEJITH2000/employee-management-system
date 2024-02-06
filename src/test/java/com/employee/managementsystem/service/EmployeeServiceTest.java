package com.employee.managementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employee.managementsystem.contract.request.EmployeeRequest;
import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.model.Employee;
import com.employee.managementsystem.repository.EmployeeRepository;
import java.util.ArrayList;
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
        Employee employee = new Employee();
        EmployeeRequest request = new EmployeeRequest();
        EmployeeResponse response = new EmployeeResponse();
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
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(response);
        EmployeeResponse result = employeeService.getEmployeeById(id);
        assertEquals(result, response);
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void searchEmployeeByDepartmentTest() {
        String department = "First department";
        List<Employee> allEmployees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(allEmployees);
        List<EmployeeResponse> retrievedEmployees =
                employeeService.searchEmployeeByDepartment(department);
        List<Employee> result =
                allEmployees.stream()
                        .filter(
                                dept ->
                                        dept.getDepartment()
                                                .toLowerCase()
                                                .contains(department.toLowerCase()))
                        .collect(Collectors.toList());
        assertEquals(result, retrievedEmployees);
        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeesByDepartment() {
        when(employeeRepository.findByDepartment(Mockito.any())).thenReturn(new ArrayList<>());
        Employee employee =
                Employee.builder()
                        .id(1L)
                        .name("Name")
                        .department("dept")
                        .email("email@gmail.com")
                        .build();

        List<EmployeeResponse> actualEmployeeByDepartment =
                employeeService.getEmployeesByDepartment("dept");
        verify(employeeRepository).findByDepartment(Mockito.any());
        assertTrue(actualEmployeeByDepartment.isEmpty());
    }
}
