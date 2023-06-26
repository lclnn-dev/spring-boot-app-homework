package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> getAllUsers() {
        List<Employee> employees = employeeService.getAll();
        return employeeMapper.toEmployeeResponseList(employees);
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeResponseDto> getPage(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging).map(e -> employeeMapper.toEmployeeResponse(e));
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. The employee founded."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    public EmployeeResponseDto getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return employeeMapper.toEmployeeResponse(employee);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeResponseDto saveEmployee(@RequestBody @Valid EmployeeRequestDto employeeRequest) {
        Employee employee = employeeMapper.toEmployeeEntity(employeeRequest);
        Employee savedEmployee = employeeService.create(employee);

        return employeeMapper.toEmployeeResponse(savedEmployee);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDto refreshEmployee(@PathVariable("id") Integer id,
                                               @RequestBody EmployeeUpdateRequestDto employeeRequest) {
        Employee employeeEntity = employeeMapper.toEmployeeEntity(employeeRequest);
        Employee updatedEmployeeEntity = employeeService.updateById(id, employeeEntity);

        return employeeMapper.toEmployeeResponse(updatedEmployeeEntity);
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSoftEmployeeById(@PathVariable Integer id) {
        employeeService.removeSoftById(id);
    }

    @PatchMapping("/users/re/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void recoverEmployeeById(@PathVariable Integer id) {
        employeeService.recoverById(id);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeResponseDto> findByCountry(@RequestParam(required = false) String country,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size,
                                                   @RequestParam(defaultValue = "") List<String> sortList,
                                                   @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        Page<Employee> pageEmployee = employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
        return pageEmployee.map(e -> employeeMapper.toEmployeeResponse(e));
    }

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> getByCountry(@RequestParam String country) {
        List<Employee> employees = employeeService.findAllByCountry(country);
        return employeeMapper.toEmployeeResponseList(employees);
    }

    @GetMapping("/users/email/n")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> getAllByEmailNull() {
        List<Employee> employees = employeeService.findAllByEmailNull();
        return employeeMapper.toEmployeeResponseList(employees);
    }

    @GetMapping("/users/country/lc")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> updateAllByCountryFirstCharLowerToUpper() {
        List<Employee> employees = employeeService.updateAllByCountryFirstCharLowerToUpper();
        return employeeMapper.toEmployeeResponseList(employees);
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/country/notin")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> getAllByCountryNotIn(@RequestParam List<String> countries) {
        List<Employee> employees = employeeService.findAllByCountryNotIn(countries);
        return employeeMapper.toEmployeeResponseList(employees);
    }

    @GetMapping("/users/deleted/ids")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> getAllDeletedByIds(@RequestParam List<Integer> ids) {
        List<Employee> employees = employeeService.findAllDeletedByIds(ids);
        return employeeMapper.toEmployeeResponseList(employees);
    }
}
