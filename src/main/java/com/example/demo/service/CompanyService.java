package com.example.demo.service;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ICompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final ICompanyRepository iCompanyRepository;

    public CompanyService(ICompanyRepository iCompanyRepository) {
        this.iCompanyRepository = iCompanyRepository;
    }

    public List<Company> getCompanies(Integer page, Integer size) {
        if (page == null || size == null) {
            return iCompanyRepository.findAll();
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return iCompanyRepository.findAll(pageable).toList();
        }
    }

    public Company createCompany(Company company) {
        return iCompanyRepository.save(company);
    }

    public Company updateCompany(int id, Company updatedCompany) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company name cannot be empty");
        }
        updatedCompany.setId(id);
        return iCompanyRepository.save(updatedCompany);
    }

    public Company getCompanyById(int id) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        return iCompanyRepository.findById(id).stream().toList().get(0);
    }

    public void deleteCompany(int id) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        iCompanyRepository.delete(company.get());
    }


}
