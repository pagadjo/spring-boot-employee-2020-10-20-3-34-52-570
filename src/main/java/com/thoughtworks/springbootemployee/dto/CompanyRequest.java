package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.models.Employee;

import java.util.List;

public class CompanyRequest {

    private String companyName;
    private List<Employee> employees;
    public CompanyRequest() {
    }

    public CompanyRequest(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
