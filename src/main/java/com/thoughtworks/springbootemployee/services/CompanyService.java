package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {
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

//    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
//        return companyRepository.findAll().stream()
//                .filter(company -> company.getCompanyId().equals(companyId))
//                .map(Company::getEmployees)
//                .findFirst()
//                .orElse(Collections.emptyList());
//    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return companyRepository.findEmployeesByCompanyId(companyId);
    }

    public Company update(Integer companyId, Company companyUpdate) {
        companyUpdate.setCompanyId(companyId);
        return companyRepository.save(companyUpdate);
    }

    public void delete(Integer companyId) {
        companyRepository.deleteById(companyId);
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageable).toList();
    }
}
