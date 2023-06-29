package com.example.demowithtests.web.controller.swagger;

import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Tag(name = "Employee", description = "Employee API")
public interface EmployeeControllerSwagger {

    @Operation(summary = "Endpoint to get all employees.",
            description = "Create request to get all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> getAllUsers();

    @Operation(summary = "Endpoint to get page of all employees.",
            description = "Create request to get page of employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. There is no any employee"),})
    Page<EmployeeResponseDto> getPage(int page, int size);

    @Operation(summary = "Endpoint returned an employee by his id.",
            description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeResponseDto getEmployeeById(Integer id);


    @Operation(summary = "Endpoint to add a new employee.",
            description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequest);

    @Operation(summary = "Endpoint to update an employee.",
            description = "Create request to update field of employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeResponseDto refreshEmployee(Integer id, EmployeeUpdateRequestDto employeeRequest);

    @Operation(summary = "Endpoint to set the deletion marker an employee.",
            description = "Create request to set the deletion marker an employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    void removeSoftEmployeeById(Integer id);

    @Operation(summary = "Endpoint to unset the deletion marker an employee.",
            description = "Create request to unset the deletion marker an employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    void recoverEmployeeById(Integer id);

    @Operation(summary = "Endpoint to delete an employee.",
            description = "Create request to delete an employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    void removeEmployeeById(Integer id);

    @Operation(summary = "Endpoint to delete all employees.",
            description = "Create request to delete employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse")})
    void removeAllUsers();

    @Operation(summary = "Endpoint to get page with all employees by country.",
            description = "Create request to to get page with all employees by country.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. There is no any employee"),})
    Page<EmployeeResponseDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Operation(summary = "Endpoint to find all employees by country.",
            description = "Create request to find all employees by country.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> getByCountry(String country);

    @Operation(summary = "Endpoint to find all employees where email = null.",
            description = "Create request to find all employees where email = null.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> getAllByEmailNull();

    @Operation(summary = "Endpoint to update all employees whose country begins with a lower char to upper char.",
            description = "Create request to to update all employees whose country begins with a lower char to upper char.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> updateAllByCountryFirstCharLowerToUpper();

    @Operation(summary = "Endpoint to find all employees where country not in list.",
            description = "Create request to find all employees where country not in list.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> getAllByCountryNotIn(List<String> countries);

    @Operation(summary = "Endpoint to find all employees where set deletion marker and id in list.",
            description = "Create request to find all employees where set deletion marker and id in list.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Successful operation"),
            @ApiResponse(responseCode = "400", ref = "badRequest")})
    List<EmployeeResponseDto> getAllDeletedByIds(List<Integer> ids);

    @Hidden
    List<String> getAllUsersC();

    @Hidden
    List<String> getAllUsersSort();
}
