package com.example.demo.dto;

public class EmployeeResponse {

    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private boolean activeStatus = true;
    private Double salary;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Integer id, String name, Integer age, String gender, boolean activeStatus, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.activeStatus = activeStatus;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public Double getSalary() {
        return salary;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
