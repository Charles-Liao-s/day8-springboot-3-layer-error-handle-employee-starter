package com.example.demo;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void should_create_a_employee_when_create_a_employee() throws InvalidAgeException {
        Employee employee = new Employee(null, "John Smith", 20, "male", 60000.0);
        when(employeeRepository.createEmployee(employee)).thenReturn(employee);
        Employee employeeResult = employeeService.createEmployee(employee);
        assertEquals(employeeResult, employee);
    }

    @Test
    public void should_throw_exception_when_create_a_employee_of_greater_than_65_or_less_than_18_years() {
        Employee employee = new Employee(null, "Jack",16, "male", 60000.0);

        assertThrows(InvalidAgeException.class, () -> {
            employeeService.createEmployee(employee);
        });
    }
    @Test
    public void should_throw_exception_when_create_a_employee_of_age_greater_than_30_years_with_salary_less_than_20000() {
        Employee employee1 = new Employee(null, "Jack",16, "male", 10000.0);
        Employee employee2 = new Employee(null, "Jack",36, "male", 40000.0);

        assertThrows(InvalidAgeException.class, () -> {
            employeeService.createEmployee(employee1);
        });

        assertThrows(InvalidAgeException.class, () -> {
            employeeService.createEmployee(employee2);
        });

    }

    @Test
    public void should_create_a_employee_with_default_active_status_when_create_a_employee_of() {
        Employee employee = new Employee(null, "Jack",20, "male", 30000.0);
        employee.setActiveStatus(true);
        when(employeeRepository.createEmployee(employee)).thenReturn(employee);
        Employee employeeResult = employeeService.createEmployee(employee);
        assertEquals(employeeResult, employee);
    }

}
