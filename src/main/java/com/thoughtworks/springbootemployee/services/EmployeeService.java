package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.InvalidEmployeeException;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        validateEmployee(newEmployee);
        return employeeRepository.save(newEmployee);
    }

    public Employee searchById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee " + id + " not found!"));
    }

    public Employee update(Integer id, Employee employeeUpdate) {
        Employee employee = searchById(id);
        if (nonNull(employeeUpdate.getName())) {
            employee.setName(employeeUpdate.getName());
        }
        if (nonNull(employeeUpdate.getGender())) {
            employee.setGender(employeeUpdate.getGender());
        }
        if (nonNull(employeeUpdate.getAge())) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (nonNull(employeeUpdate.getAge())) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (nonNull(employeeUpdate.getSalary())) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        if (nonNull(employeeUpdate.getCompanyId())) {
            employee.setCompanyId(employeeUpdate.getCompanyId());
        }
        employeeRepository.save(employee);

        return employee;
    }

    private void validateEmployee(Employee employee) {
        if (isNull(employee.getName()) || isNull(employee.getGender())) {
            throw new InvalidEmployeeException("Employee data is not valid!");
        }
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
