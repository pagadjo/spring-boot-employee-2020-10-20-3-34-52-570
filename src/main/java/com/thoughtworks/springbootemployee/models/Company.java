package com.thoughtworks.springbootemployee.models;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return null;
    }

    public void addEmployee(Employee employee) {
    }

    public int getEmployeeNumber() {
        return 0;
    }
}
