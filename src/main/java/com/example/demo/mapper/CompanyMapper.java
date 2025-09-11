package com.example.demo.mapper;

import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class CompanyMapper {
    public CompanyResponse toCompanyResponse(Company company) {
        CompanyResponse response = new CompanyResponse();
        BeanUtils.copyProperties(company, response);
        return response;
    }

    public List<CompanyResponse> toEmployeeResponseList(List<Company> company) {
        return company.stream().map(this::toCompanyResponse).toList();
    }
}
