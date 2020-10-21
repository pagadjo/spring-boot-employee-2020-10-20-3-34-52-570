package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.models.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private static final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @GetMapping("/{employeeId}")
    public Employee searchById(@PathVariable("employeeId") String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable("employeeId") String employeeId, @RequestBody Employee updatedEmployee) {
        employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employee -> {
            employees.remove(employee);
            employees.add(updatedEmployee);
        });
        return updatedEmployee;
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable("employeeId") String employeeId) {
        employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employees::remove);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }
}
