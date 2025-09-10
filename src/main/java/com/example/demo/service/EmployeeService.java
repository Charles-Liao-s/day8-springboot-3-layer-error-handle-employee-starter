package com.example.demo.service;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        return employeeRepository.getEmployees(gender, page, size);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    public Employee createEmployee(Employee employee) throws InvalidAgeException {
        if (employee.getAge() == null) {
            throw new InvalidAgeException("Employee age cannot be null");
        }
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidAgeException("Employee age should be between 18 and 65");
        }
        if (employee.getAge() > 30 || employee.getSalary() < 20000) {
            throw new InvalidAgeException("Employee age should be less than 30 and salary shoule be gerater than 20000");
        }
        return employeeRepository.createEmployee(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        if (existingEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated employee cannot be null");
        }
        if (!existingEmployee.isActiveStatus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not active with id: " + id);
        }
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        if (!employee.isActiveStatus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not active with id: " + id);
        }
        employee.setActiveStatus(false);
        employeeRepository.updateEmployee(id, employee);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAllEmployees();
    }

    public void empty() {
        employeeRepository.empty();
    }
}
