package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepositoryLegacy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Employee searchById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee update(Integer id, Employee employeeUpdate) {
        Employee employee = searchById(id);
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
        Employee employee = searchById(id);
        if (isNull(employee)) {
            return;
        }
        employeeRepository.delete(employee);
    }

    public List<Employee> searchByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeeByPageAndPageSize(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return employeeRepository.findAll(pageable).toList();
    }
}
