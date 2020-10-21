package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    public EmployeeService(EmployeeRepository repository) {
    }

    public List<Employee> getAll() {
        return null;
    }
}
