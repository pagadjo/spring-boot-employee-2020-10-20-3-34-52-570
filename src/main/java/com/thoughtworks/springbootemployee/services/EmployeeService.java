package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class EmployeeService {

    private EmployeeRepositoryLegacy employeeRepositoryLegacy;
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Optional<Employee> searchById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Employee update(Integer id, Employee employeeUpdate) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (isNull(employee)) {
            return null;
        }
        employee.setName(employeeUpdate.getName());
        employee.setAge(employeeUpdate.getAge());
        employee.setGender(employeeUpdate.getGender());
        employee.setSalary(employeeUpdate.getSalary());
        return employeeRepository.save(employee);
    }

    public void delete(Integer id) {
        employeeRepositoryLegacy.delete(id);
    }

    public List<Employee> searchByGender(String gender) {
        return employeeRepositoryLegacy.findByGender(gender);
    }

    public List<Employee> getEmployeeByPageAndPageSize(int page, int pageSize) {
        return employeeRepositoryLegacy.getAll().stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
