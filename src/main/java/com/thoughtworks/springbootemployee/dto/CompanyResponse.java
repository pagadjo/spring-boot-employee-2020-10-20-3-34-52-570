package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.models.Employee;

import java.util.List;

public class CompanyResponse {

    private Integer companyId;
    private String companyName;
    private List<Employee> employees;
    private int employeeNumber;

    public CompanyResponse() {
    }

    public CompanyResponse(String companyName) {
        this.companyName = companyName;
    }

    public CompanyResponse(Integer companyId, String companyName, List<Employee> employees, int employeeNumber) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employees = employees;
        this.employeeNumber = employeeNumber;
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

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
