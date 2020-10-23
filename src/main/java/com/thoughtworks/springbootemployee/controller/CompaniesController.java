package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.models.Company;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;

    public CompaniesController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.getAll().stream().map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CompanyRequest companyRequest) {
        Company company = companyService.create(companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(company);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse searchById(@PathVariable("companyId") Integer companyId) {
        return companyMapper.toResponse(companyService.searchByCompanyId(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable("companyId") Integer companyId) {
        return companyService.getEmployeesByCompanyId(companyId)
                .stream().map(employeeMapper::toResponse).collect(Collectors.toList());

//        return companyService.getEmployeesByCompanyId(companyId).stream().map(employee -> )
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable("companyId") Integer companyId,
                                  @RequestBody CompanyRequest updatedCompanyRequest) {
        return companyMapper.toResponse(
                companyService.update(companyId, companyMapper.toEntity(updatedCompanyRequest)));
    }

    @DeleteMapping("/{companyId}")
    public void delete(@PathVariable("companyId") Integer companyId) {
        companyService.delete(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPageAndPageSize(@RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize) {
        return companyService.getCompaniesByPageAndPageSize(page, pageSize)
                .stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }
}
