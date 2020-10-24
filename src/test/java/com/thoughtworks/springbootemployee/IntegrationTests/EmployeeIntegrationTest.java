package com.thoughtworks.springbootemployee.IntegrationTests;

import com.google.gson.Gson;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class EmployeesIntegrationTest {

    private Gson gson;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @BeforeEach
    void setup(){
        gson = new Gson();
    }

    @Test
    void should_get_all_employees_when_get_all() throws Exception {
        //given
        Employee employee = new Employee(1, "JC", 12, "male", 10000);
        employeeRepository.save(employee);

        //when then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("JC"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(10000));
    }

    @Test
    void should_create_employee_when_create_given_employee() throws Exception {
        //given
        Employee employee = new Employee("Janelle", 21, "female", 100000);
        String jsonEmployee = gson.toJson(employee, Employee.class);

        //when then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Janelle"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(100000));
    }

    @Test
    void should_get_employee_with_id_1_when_search_by_id_given_employee_with_id_1() throws Exception {
        //given
        Employee employee = new Employee(1, "Janelle", 21, "female", 100000);
        employeeRepository.save(employee);

        //when then
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(100000));
    }

    @Test
    void should_get_updated_employee_when_update_employee_given_employee() throws Exception {
        //given
        Employee employee = new Employee(1, "Janelle", 21, "female", 100000);
        employeeRepository.save(employee);

        Employee updateEmployee = new Employee("Charlie", 20, "male", 1000);
        String jsonEmployee = gson.toJson(updateEmployee, Employee.class);

        //when then
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1000));
    }

    @Test
    void should_delete_employee_when_deleted_given_employee_id_1() throws Exception {
        //given
        Employee employee = new Employee("Janelle", 21, "female", 100000);
        employeeRepository.save(employee);

        //when then
        mockMvc.perform(delete("/employees/{employeeId}",9)).andExpect(status().isOk());

        Employee employee1 = employeeRepository.findById(9).orElse(null);
        assertNull(employee1);
    }

    @Test
    void should_return_all_male_when_filtered_gender_given_male_() throws Exception {
        //given
        Employee firstEmployee = new Employee(1, "Janelle", 21, "female", 10000000);
        Employee secondEmployee = new Employee(2, "Jc", 20, "male", 2000000);
        Employee thirdEmployee = new Employee(3, "Jc", 20, "male", 2000000);
        employeeRepository.save(firstEmployee);
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);

        //when then
        mockMvc.perform(get("/employees?gender=male")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Jc"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(2000000));

        List<Employee> actual = employeeRepository.findByGender("male");
        assertEquals(2, actual.size());
    }

    @Test
    void should_return_3_employees_filtered_by_page_and_pageSize__given_4_employees_page_0_and_pageSize_3() throws Exception {
        //given
        Employee firstEmployee = new Employee(1, "Janelle", 21, "female", 10000000);
        Employee secondEmployee = new Employee(2, "Jc", 20, "male", 2000000);
        Employee thirdEmployee = new Employee(3, "Cedric", 20, "male", 2000000);
        Employee fourthEmployee = new Employee(4, "Joseph", 20, "male", 2000000);
        employeeRepository.save(firstEmployee);
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
        employeeRepository.save(fourthEmployee);

        //when then
        mockMvc.perform(get("/employees?page=0&pageSize=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Janelle"))
                .andExpect(jsonPath("$[0].age").value(21))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[0].salary").value(10000000));

        Pageable pageable = PageRequest.of(0, 3);
        List<Employee> actual = employeeRepository.findAll(pageable).toList();
        assertEquals(3, actual.size());
    }
}
