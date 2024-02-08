package com.employee.managementsystem.service;

import com.employee.managementsystem.contract.request.EmployeeRequest;
import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.exception.EmailAlreadyExistsException;
import com.employee.managementsystem.exception.EmployeeNotFoundException;
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
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists..😒😒..try another one");
        }
        Employee employee = employeeRepository.save(modelMapper.map(request, Employee.class));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse getEmployeeById(long id) {
        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElseThrow(() ->  new EmployeeNotFoundException("Employee not found"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()){
            throw new EmployeeNotFoundException("employee not found..😒😒");
        }
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }
}
