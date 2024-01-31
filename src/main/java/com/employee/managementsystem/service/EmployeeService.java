package com.employee.managementsystem.service;

import com.employee.managementsystem.contract.request.EmployeeRequest;
import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.model.Employee;
import com.employee.managementsystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee employee = employeeRepository.save(modelMapper.map(request, Employee.class));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }
    public List<Employee> searchEmployeeByDept(String dept) {
        List<Employee> employees = this.employeeRepository.findAll();
        List<Employee> retrievedEmployees = employees.stream()
                .filter(employee -> employee.getDepartment().toLowerCase().contains(dept.toLowerCase()))
                .collect(Collectors.toList());
        return retrievedEmployees;
    }
}