package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.models.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static java.util.Objects.nonNull;

@Component
public class CompanyMapper {
    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        boolean companyHasEmployee = nonNull(company.getEmployees());
        companyResponse.setEmployeeNumber(companyHasEmployee ? company.getEmployees().size() : 0);
        companyResponse.setEmployees(companyHasEmployee ? company.getEmployees() : Collections.emptyList());
        return companyResponse;
    }

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }
}
