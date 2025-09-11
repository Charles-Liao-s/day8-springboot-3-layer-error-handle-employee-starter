package com.example.demo.mapper;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.List;


public class EmployeeMapper {
    public EmployeeResponse toEmployeeResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(employee, response);
        return response;
    }

    public List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees) {
        return employees.stream().map(this::toEmployeeResponse).toList();
    }

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public List<Employee> toEmployeeList(List<EmployeeRequest> employeeRequests) {
        return employeeRequests.stream().map(this::toEmployee).toList();
    }

}
