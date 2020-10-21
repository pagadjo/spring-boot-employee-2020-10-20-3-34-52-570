package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    void should_return_2_companies_when_get_companies_given_2_companies() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.getAll()).thenReturn(asList(new Company(""), new Company("")));
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Integer employeeCount = companyService.getAll().size();

        //then
        assertEquals(2, employeeCount);
    }

    @Test
    void should_return_company_with_name_OOCL_when_create_given_company_with_name_OOCL() {
        //given
        Company newCompany = new Company("OOCL");
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.create(newCompany)).thenReturn(newCompany);
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Company company = companyService.create(newCompany);

        //then
        assertEquals("OOCL", company.getCompanyName());
        assertSame(newCompany, company);
    }

}