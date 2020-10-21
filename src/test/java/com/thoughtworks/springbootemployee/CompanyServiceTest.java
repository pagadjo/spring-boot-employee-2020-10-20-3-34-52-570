package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.models.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    void should_return_2_companies_when_get_companies_given_2_companies() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.getAll()).thenReturn(asList(new Company(1, "OOCL"), new Company(2, "SM")));
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Integer employeeCount = companyService.getAll().size();

        //then
        assertEquals(2, employeeCount);
    }

    @Test
    void should_return_company_with_name_OOCL_when_create_given_company_with_name_OOCL() {
        //given
        Company newCompany = new Company(1, "OOCL");
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.create(newCompany)).thenReturn(newCompany);
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Company company = companyService.create(newCompany);

        //then
        assertEquals("OOCL", company.getCompanyName());
        assertSame(newCompany, company);
    }

    @Test
    void should_return_company_with_name_OOCL_employees_2_employeesNumber_2_when_create_given_company_with_name_OOCL_employees_2() {
        //given
        Company newCompany = new Company(1, "OOCL");
        newCompany.addEmployee(new Employee());
        newCompany.addEmployee(new Employee());

        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.create(newCompany)).thenReturn(newCompany);
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Company company = companyService.create(newCompany);

        //then
        assertEquals("OOCL", company.getCompanyName());
        assertSame(newCompany, company);
        assertEquals(2, company.getEmployeeNumber());
    }

    @Test
    void should_return_company_when_searchByCompanyId_given_company_with_id_of_1() {
        //given
        Company company = new Company(1, "OOCL");
        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.findById(company.getId())).thenReturn(company);
        CompanyService companyService = new CompanyService(repository);

        //when
        Company fetchedCompany = companyService.searchById(company.getId());

        //then
        assertNotNull(company);
        assertSame(company, fetchedCompany);
    }
}