package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    public CompanyService(CompanyRepository companyRepository) {
    }

    public List<Company> getAll() {
        return null;
    }
}
