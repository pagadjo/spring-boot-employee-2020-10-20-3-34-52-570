package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.models.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        BeanUtils.copyProperties(employee, employeeResponse);
        employeeResponse.setId(employee.getId());
        return employeeResponse;
    }

    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }
}
