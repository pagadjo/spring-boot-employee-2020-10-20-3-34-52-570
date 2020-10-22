package com.thoughtworks.springbootemployee.IntegrationTests;

import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmployeesIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
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
        String employeeJSON = "{\n" +
                "    \"name\" : \"Janelle\",\n" +
                "    \"age\" : 21,\n" +
                "    \"gender\" : \"female\",\n" +
                "    \"salary\" : 100000\n" +
                "}";

        //when then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJSON))
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

        String employeeJSON = "{\n" +
                "    \"name\" : \"JC\",\n" +
                "    \"age\" : 20,\n" +
                "    \"gender\" : \"male\",\n" +
                "    \"salary\" : 100\n" +
                "}";

        //when then
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("JC"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(100));
    }

    @Test
    void should_delete_employee_when_deleted_given_employee_id_1() throws Exception {
        //given
        Employee employee = new Employee(1, "Janelle", 21, "female", 100000);
        employeeRepository.save(employee);

        //when then
        mockMvc.perform(delete("/employees/{employeeId}", 1)).andExpect(status().isOk());

        Employee employee1 = employeeRepository.findById(1).orElse(null);
        assertNull(employee1);
    }
}
