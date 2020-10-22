package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @GetMapping("/{employeeId}")
    public Employee searchById(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.searchById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable("employeeId") Integer employeeId, @RequestBody Employee updatedEmployee) {
        return employeeService.update(employeeId, updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable("employeeId") Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam("gender") String gender) {
        return employeeService.searchByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByEmployeeByPage(@RequestParam("page") Integer page,
                                              @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getEmployeeByPageAndPageSize(page, pageSize);
    }
}
