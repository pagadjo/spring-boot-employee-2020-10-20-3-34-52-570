package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.InvalidCompanyException;
import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

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
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company " + companyId + " not found!"));
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return companyRepository.findEmployeesByCompanyId(companyId);
    }

    public Company update(Integer companyId, Company companyUpdate) {
        Company company = searchByCompanyId(companyId);
        validateCompany(companyUpdate);
        company.setCompanyName(companyUpdate.getCompanyName());
        companyRepository.save(company);
        return company;
    }

    private void validateCompany(Company companyUpdate) {
        if (isNull(companyUpdate.getCompanyName())) {
            throw new InvalidCompanyException("Company data is not valid!");
        }
    }

    public void delete(Integer companyId) {
        companyRepository.deleteById(companyId);
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageable).toList();
    }
}
