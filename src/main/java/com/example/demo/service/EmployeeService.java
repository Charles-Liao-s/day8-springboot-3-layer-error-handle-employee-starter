package com.example.demo.service;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.entity.Employee;
import com.example.demo.repository.IEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
//    private final EmployeeRepository employeeRepository;
//
//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
    private final IEmployeeRepository iEmployeeRepository;

    public EmployeeService(IEmployeeRepository iEmployeeRepository) {
        this.iEmployeeRepository = iEmployeeRepository;
    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        if(gender == null) {
            if(page == null || size == null) {
                return iEmployeeRepository.findAll();
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return iEmployeeRepository.findAll(pageable).toList();
            }
        }else{
            if(page == null || size == null) {
                return iEmployeeRepository.findEmployeesByGender(gender);
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return iEmployeeRepository.findEmployeesByGender(gender, pageable);
            }
        }
    }

    public Employee getEmployeeById(Integer id) {
        Optional<Employee> employee = iEmployeeRepository.findById(id);
        if(employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee id cannot be null");
        }
        return employee.get();
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
        employee.setActiveStatus(true);
        return iEmployeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> employee = iEmployeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Updated employee cannot be null");
        }
        if (!employee.get().isActiveStatus()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not active with id: " + id);
        }
        updatedEmployee.setId(id);
        return iEmployeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = iEmployeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        if (!employee.get().isActiveStatus()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not active with id: " + id);
        }
        employee.get().setActiveStatus(false);
        iEmployeeRepository.save(employee.get());
    }
}
