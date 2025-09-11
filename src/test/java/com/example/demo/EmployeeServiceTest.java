package com.example.demo;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private IEmployeeRepository iemployeeRepository;

    @Test
    public void should_create_a_employee_when_create_a_employee() throws InvalidAgeException {
        Employee employee = new Employee(null, "John Smith", 20, "male", 60000.0);
        when(iemployeeRepository.save(employee)).thenReturn(employee);
        employeeService.createEmployee(employee);
        verify(iemployeeRepository).save(employee);
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
        Employee employee1 = new Employee(1, "Jack",20, "male", 30000.0);
        when(iemployeeRepository.save(employee1)).thenReturn(employee1);
        EmployeeResponse employeeResult = employeeService.createEmployee(employee1);
        assertTrue(employeeResult.isActiveStatus());
        verify(iemployeeRepository).save(argThat(Employee::isActiveStatus));
    }

    @Test
    public void should_set_active_status_to_false_when_delete_a_employee() {
        Employee employee = new Employee(1, "John", 28, "male", 60000.0);
        assertTrue(employee.isActiveStatus());
        when(iemployeeRepository.findById(1)).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(1);
        verify(iemployeeRepository).save(argThat(emp -> !emp.isActiveStatus()));
    }

    @Test
    public void should_throw_exception_when_update_a_employee_with_status_false() {
        Employee employee = new Employee(1, "John", 20, "male", 60000.0);
        when(iemployeeRepository.findById(1)).thenReturn(Optional.of(employee));
        employee.setActiveStatus(false);
        Employee updatedEmployee = new Employee(1, "John",28, "male", 60000.0);
        assertThrows(Exception.class, () -> {
            employeeService.updateEmployee(1, updatedEmployee);
        });

    }

}
