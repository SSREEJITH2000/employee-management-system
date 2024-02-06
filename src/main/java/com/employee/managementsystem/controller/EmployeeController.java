package com.employee.managementsystem.controller;

import com.employee.managementsystem.contract.request.EmployeeRequest;
import com.employee.managementsystem.contract.response.EmployeeResponse;
import com.employee.managementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public @ResponseBody EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest request) {
        return this.employeeService.addEmployee(request);
    }

    @GetMapping("/{id}")
    public @ResponseBody EmployeeResponse getEmployeeById(@PathVariable long id) {
        return this.employeeService.getEmployeeById(id);
    }

    @GetMapping("/search")
    public @ResponseBody List<EmployeeResponse> searchEmployeeByDepartment(
            @RequestParam String department) {
        return this.employeeService.searchEmployeeByDepartment(department);
    }

    @GetMapping("/get")
    public List<EmployeeResponse> getEmployeesByDepartment(@RequestParam String department) {
        return employeeService.getEmployeesByDepartment(department);
    }
}
