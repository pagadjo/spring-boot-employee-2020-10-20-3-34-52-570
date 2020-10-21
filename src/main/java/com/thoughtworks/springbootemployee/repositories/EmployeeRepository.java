package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
   private List<Employee> employees = new ArrayList<>();

    public List<Employee> getAll() {
        return employees;
    }
}
