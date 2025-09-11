package com.example.demo.repository;

import com.example.demo.entity.Employee;

import java.util.List;

public class EmployeeRepositoryFinal implements IEmployeeRepositoryFinal {

    private final IEmployeeRepository employeeRepository;

    public EmployeeRepositoryFinal(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return null;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        return null;
    }

    @Override
    public void deleteEmployee(int id) {

    }
}
