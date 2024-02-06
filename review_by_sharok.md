# Service Class

1. In **addEmployee()** method, it's good to include an exception for the case where the email ID already exists.
2. In line no.26, **getEmployeeById()** method, It's advisable to use a custom exception class and apply it wherever needed.
3. In line no.33, **searchEmployeeByDepartment()** method, retrieves all employees and then filters them instead of directly queries the repository for employees with the specified department. Actually filteration is not mentioned in question.      
4. In line no.51, **getEmployeesByDepartment()** method, It's advisable to throw an exception when the employees are not present in given department.

# Controller class

1. In line no. 34, **searchEmployeeByDepartment()** method is not required as per question. 

# EmployeeServiceTest

1. In line no. 63, **searchEmployeeByDepartmentTest()** method is not required.

# EmployeeResponse 

1. _@Builder_ is not used.

#  ManagementSystemApplication

1. In line no. 18, **openAPI()** is not necessary.





