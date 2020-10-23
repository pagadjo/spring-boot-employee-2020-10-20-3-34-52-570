package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeesController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll().stream().map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        Employee newEmployee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(newEmployee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse searchById(@PathVariable("employeeId") Integer employeeId) {
        return employeeMapper.toResponse(employeeService.searchById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable("employeeId") Integer employeeId, @RequestBody EmployeeRequest updatedEmployeeRequest) {
        return employeeMapper.toResponse(
                employeeService.update(employeeId, employeeMapper.toEntity(updatedEmployeeRequest)));
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable("employeeId") Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam("gender") String gender) {
        return employeeService.searchByGender(gender).stream()
            .map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getByEmployeeByPage(@RequestParam("page") Integer page,
                                              @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getEmployeeByPageAndPageSize(page, pageSize)
                .stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }
}
