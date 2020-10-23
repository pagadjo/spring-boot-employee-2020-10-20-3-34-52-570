package com.thoughtworks.springbootemployee.IntegrationTests;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
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
class CompanyIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_get_all_employees_when_get_all() throws Exception {
        //given
        Company company = new Company(1, "OOCL");
        companyRepository.save(company);

        //when then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    void should_return_created_company_when_create_a_company() throws Exception {
        //given
//        String companyJson = "{\n" +
//                "    \"companyName\": \"OOCL\",\n" +
//                "    \"employees\": [\n" +
//                "        {\n" +
//                "            \"name\": \"pagadjo\",\n" +
//                "            \"age\": 10,\n" +
//                "            \"gender\": \"male\",\n" +
//                "            \"salary\": 100000000\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"name\": \"manalch\",\n" +
//                "            \"age\": 10,\n" +
//                "            \"gender\": \"male\",\n" +
//                "            \"salary\": 100000000\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";

        String jsonCompany = "{\n" +
                "    \"companyName\":\"OOCL\"\n" +
                "}";


        //when then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyName").value("OOCL"));
//                .andExpect(jsonPath("$.employees").isNotEmpty());
    }

    @Test
    void should_get_company_with_company_id_1_when_search_by_id_given_company_with_id_1() throws Exception {
        //given
        Company company = new Company(1, "COSCO");
        companyRepository.save(company);

        //when then
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyId").value(1))
                .andExpect(jsonPath("$.companyName").value("COSCO"))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_get_employees_of_company_with_company_id_1_when_search_by_id_given_company_with_id_1() throws Exception {
        //given
//        String companyJson = "{\n" +
//                "    \"companyName\": \"OOCL\",\n" +
//                "    \"employees\": [\n" +
//                "        {\n" +
//                "            \"name\": \"pagadjo\",\n" +
//                "            \"age\": 10,\n" +
//                "            \"gender\": \"male\",\n" +
//                "            \"salary\": 100000000\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"name\": \"manalch\",\n" +
//                "            \"age\": 21,\n" +
//                "            \"gender\": \"male\",\n" +
//                "            \"salary\": 800000000\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";

        String jsonCompany = "{\n" +
                "    \"companyName\":\"OOCL\"\n" +
                "}";


        String jsonEmployees = "{\n" +
                "    \"name\" : \"CEDRIC\",\n" +
                "    \"age\" : 21,\n" +
                "    \"gender\" : \"male\",\n" +
                "    \"salary\" : 100000,\n" +
                "    \"companyId\": 2\n" +
                "}";

        //when then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEmployees))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companies/2/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("CEDRIC"))
                .andExpect(jsonPath("$[0].age").value(21))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(100000));
//                .andExpect(jsonPath("$[1].id").isNumber())
//                .andExpect(jsonPath("$[1].name").value("manalch"))
//                .andExpect(jsonPath("$[1].age").value(21))
//                .andExpect(jsonPath("$[1].gender").value("male"))
//                .andExpect(jsonPath("$[1].salary").value(800000000));
    }

    @Test
    void should_get_updated_company_when_given_new_company_name() throws Exception {
        //given
        Company company = new Company(1, "COSCO");
        companyRepository.save(company);

        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"pagadjo\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 100000000\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"manalch\",\n" +
                "            \"age\": 21,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 800000000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when then
        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyId").value(1))
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_delete_employee_when_deleted_given_employee_id_1() throws Exception {
        //given
        Company company = new Company(1, "COSCO");
        companyRepository.save(company);

        //when then
        mockMvc.perform(delete("/companies/{employeeId}", 1)).andExpect(status().isOk());

        Company company1 = companyRepository.findById(1).orElse(null);
        assertNull(company1);
    }

    @Test
    void should_return_3_employees_filtered_by_page_and_pageSize__given_4_employees_page_0_and_pageSize_3() throws Exception {
        //given
        Company OOCL = new Company(1, "OOCL");
        Company COSCO = new Company(2, "COSCO");
        Company COSCOLogistics = new Company(3, "COSCOLogistics");
        companyRepository.save(OOCL);
        companyRepository.save(COSCO);
        companyRepository.save(COSCOLogistics);

        //when then
        mockMvc.perform(get("/companies?page=0&pageSize=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyId").value(1))
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[1].companyId").isNumber())
                .andExpect(jsonPath("$[1].companyId").value(2))
                .andExpect(jsonPath("$[1].companyName").value("COSCO"))
                .andExpect(jsonPath("$[2]").doesNotExist());

        Pageable pageable = PageRequest.of(0, 2);
        List<Company> actual = companyRepository.findAll(pageable).toList();
        assertEquals(2, actual.size());
    }
}
