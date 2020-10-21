package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
