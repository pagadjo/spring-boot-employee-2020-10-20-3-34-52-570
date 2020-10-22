package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.CompanyRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private CompanyRepositoryLegacy companyRepositoryLegacy;
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company create(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public Company searchByCompanyId(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return companyRepositoryLegacy.getAll()
                .stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .map(Company::getEmployees)
                .orElse(Collections.emptyList());
    }

    public Company update(Integer companyId, Company companyUpdate) {
        companyUpdate.setCompanyId(companyId);
        return companyRepository.save(companyUpdate);
    }

    public void delete(Integer id) {
        companyRepositoryLegacy.delete(id);
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyRepositoryLegacy.getAll().stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
