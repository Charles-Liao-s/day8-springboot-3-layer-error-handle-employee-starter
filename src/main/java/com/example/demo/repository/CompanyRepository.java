package com.example.demo.repository;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public void empty() {
        companies.clear();
    }

    public List<Company> getCompanies(Integer page, Integer size) {
        if (page != null && size != null) {
            int start = (page - 1) * size;
            int end = Math.min(start + size, companies.size());
            if (start >= companies.size()) {
                return new ArrayList<>();
            }
            return companies.subList(start, end);
        }
        return companies;
    }

    public Company createCompany(Company company) {
        company.setId(1);
        companies.add(company);
        return company;
    }

    public Company updateCompany(int id, Company updatedCompany) {
        for (Company c : companies) {
            if (c.getId().equals(id)) {
                c.setName(updatedCompany.getName());
                return c;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
    }

    public Company getCompanyById(int id) {
        for (Company c : companies) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void deleteCompany(int id) {
        Company found = null;
        for (Company c : companies) {
            if (c.getId().equals(id)) {
                found = c;
                break;
            }
        }
        if (found != null) {
            companies.remove(found);
        }
    }


}