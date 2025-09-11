package com.example.demo.service;

import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.repository.ICompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final ICompanyRepository iCompanyRepository;

    private final CompanyMapper companyMapper = new CompanyMapper();

    public CompanyService(ICompanyRepository iCompanyRepository) {
        this.iCompanyRepository = iCompanyRepository;
    }

    public List<CompanyResponse> getCompanies(Integer page, Integer size) {
        if (page == null || size == null) {
            return companyMapper.toEmployeeResponseList(iCompanyRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return companyMapper.toEmployeeResponseList(iCompanyRepository.findAll(pageable).toList());
        }
    }

    public CompanyResponse createCompany(Company company) {
        return companyMapper.toCompanyResponse(iCompanyRepository.save(company));
    }

    public CompanyResponse updateCompany(int id, Company updatedCompany) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company name cannot be empty");
        }
        updatedCompany.setId(id);
        return companyMapper.toCompanyResponse(iCompanyRepository.save(updatedCompany));
    }

    public CompanyResponse getCompanyById(int id) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        return companyMapper.toCompanyResponse(iCompanyRepository.findById(id).stream().toList().get(0));
    }

    public void deleteCompany(int id) {
        Optional<Company> company = iCompanyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        iCompanyRepository.delete(company.get());
    }


}
