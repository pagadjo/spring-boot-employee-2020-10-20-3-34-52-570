package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompanyService companyService;

    public CompaniesController(CompanyService companyService) {
        this.companyService = companyService;

    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        return companyService.create(company);
    }
}
