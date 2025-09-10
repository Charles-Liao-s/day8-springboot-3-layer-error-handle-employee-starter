package com.example.demo.repository;

import com.example.demo.Exception.InvalidStatusException;
import com.example.demo.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees( String gender ,Integer page,Integer size) {
        Stream<Employee> stream = employees.stream();
        if (gender != null) {
            stream = stream.filter(employee -> employee.getGender().compareToIgnoreCase(gender) == 0);
        }
        if (page != null && size != null) {
            stream = stream.skip((long) (page - 1) * size).limit(size);
        }
        return stream.toList();
    }

    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employee.setActiveStatus(true);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        updatedEmployee.setName(updatedEmployee.getName());
        updatedEmployee.setAge(updatedEmployee.getAge());
        updatedEmployee.setGender(updatedEmployee.getGender());
        updatedEmployee.setSalary(updatedEmployee.getSalary());
        return updatedEmployee;
    }

    public void deleteEmployee(int id) {
        Employee found = null;
        for (Employee e : employees) {
            if (e.getId() == id) {
                found = e;
                break;
            }
        }
        if (found == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        found.setActiveStatus(false);
    }

    public void deleteAllEmployees() {
        employees.clear();
    }

    public void empty() {
        employees.clear();
    }


}
