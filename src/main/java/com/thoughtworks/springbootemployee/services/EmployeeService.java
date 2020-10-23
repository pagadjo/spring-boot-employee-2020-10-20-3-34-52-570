package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.InvalidEmployeeException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> employeeMapper.toResponse(employee)).collect(Collectors.toList());
    }

    public EmployeeResponse create(EmployeeRequest newEmployeeRequest) {
        Employee employee = employeeRepository.save(employeeMapper.toEntity(newEmployeeRequest));
        return employeeMapper.toResponse(employee);
    }

    public Employee searchById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee " + id + " not found!"));
    }

    public Employee update(Integer id, Employee employeeUpdate) {
        Employee employee = searchById(id);
        validateEmployee(employee);
        employee.setName(employeeUpdate.getName());
        employee.setGender(employeeUpdate.getGender());
        if (nonNull(employeeUpdate.getAge())) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (nonNull(employeeUpdate.getSalary())) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        if (nonNull(employeeUpdate.getCompanyId())) {
            employee.setCompanyId(employeeUpdate.getCompanyId());
        }
        return employeeRepository.save(employee);
    }

    public void validateEmployee(Employee employee) {
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
