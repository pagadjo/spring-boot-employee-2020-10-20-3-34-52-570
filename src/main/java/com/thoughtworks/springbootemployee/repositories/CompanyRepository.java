package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.models.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private final List<Company> companies = new ArrayList<>();

    public List<Company> getAll() {
        return companies;
    }

    public Company create(Company newCompany) {
        return null;
    }
}
