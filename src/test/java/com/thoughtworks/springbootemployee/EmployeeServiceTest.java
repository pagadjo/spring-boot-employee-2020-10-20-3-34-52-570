package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Test
    void should_return_2_when_get_employees_given_2_employees() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        when(repository.getAll()).thenReturn(Arrays.asList(new Employee(), new Employee()));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Integer employeeCount = employeeService.getAll().size();

        //then
        Assertions.assertEquals(2, employeeCount);
    }

    @Test
    void should_return_employee_with_id_1_when_create_given_employee_with_id_of_1() {
        //given
        Employee newEmployee = new Employee(1, "", 20, "male", 1000);
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        when(repository.create(newEmployee)).thenReturn(newEmployee);
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Employee employee = employeeService.create(newEmployee);

        //then
        Assertions.assertEquals(1, employee.getId());
    }

    @Test
    void should_return_employee_when_searchById_given_employee_with_id_of_1(){
        //given
        Employee employee = new Employee(1, "", 20, "male", 1000);
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        when(repository.findById(employee.getId())).thenReturn(employee);
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Employee fetchedEmployee = employeeService.searchById(employee.getId());

        //then
        assertNotNull(employee);
        assertSame(employee,fetchedEmployee);
    }
}