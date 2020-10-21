package com.thoughtworks.springbootemployee.models;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private Integer id;
    private String companyName;
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(Integer id, String companyName) {
        this.id = id;
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

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getEmployeeNumber() {
        return employees.size();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
