package com.example.demo.repository;

import com.example.demo.entity.Employee;

import java.util.List;

public interface IEmployeeRepositoryFinal {

    public List<Employee> getEmployees(String gender , Integer page, Integer size) ;


    public Employee getEmployeeById(Integer id) ;

    public Employee createEmployee(Employee employee);

    public Employee updateEmployee(int id, Employee updatedEmployee) ;

    public void deleteEmployee(int id);
}
