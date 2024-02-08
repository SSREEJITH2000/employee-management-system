package com.employee.managementsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private long id;
    private String name;
    private String email;
    private String department;
}
