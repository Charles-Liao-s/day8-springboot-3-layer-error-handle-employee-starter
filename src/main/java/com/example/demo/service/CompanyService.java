package com.example.demo.service;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies(Integer page, Integer size) {
        return companyRepository.getCompanies(page, size);
    }

    public Company createCompany(Company company) {
        return companyRepository.createCompany(company);
    }

    public Company updateCompany(int id, Company updatedCompany) {
        return companyRepository.updateCompany(id, updatedCompany);
    }

    public Company getCompanyById(int id) {
        return companyRepository.getCompanyById(id);
    }

    public void deleteCompany(int id) {
        companyRepository.deleteCompany(id);
    }

    public void empty() {
        companyRepository.empty();
    }
}
