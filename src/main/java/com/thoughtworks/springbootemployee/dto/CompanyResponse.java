package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompanyResponse {

    private Integer companyId;
    private String companyName;
    private Integer employeeNumber;

    private List<Employee> employees = new ArrayList<>();

    public CompanyResponse() {
    }

    public CompanyResponse(Integer companyId, String companyName) {
        this.companyId = companyId;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
