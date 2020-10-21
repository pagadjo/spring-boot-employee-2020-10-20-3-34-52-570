package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    public Company create(Company newCompany) {
        return companyRepository.create(newCompany);
    }
}
