package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.mapper.EmployeeMapper;
import com.example.demowithtests.web.EmployeeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@DisplayName("Employee Controller Tests")
class EmployeeControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    EmployeeMapper employeeMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/users")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmployeeResponseDto_StatusCreated_AfterSaveEmployee() throws Exception {

        EmployeeResponseDto response = new EmployeeResponseDto(
                1, "Mike", "England", "mail@mail.com",
                null, null, null, false);

        Employee employee = Employee.builder()
                .id(1)
                .name("Mike")
                .email("mail@mail.com").build();

        when(employeeMapper.toEmployeeEntity(any(EmployeeRequestDto.class))).thenReturn(employee);
        when(employeeService.create(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toEmployeeResponse(employee)).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mike"));

        verify(employeeService).create(any());
    }

    @Test
    @DisplayName("GET /api/users/{id}")
    @WithMockUser(roles = "USER")
    void shouldReturnEmployeeResponseDto_StatusOk_AfterGetEmployeeById() throws Exception {

        EmployeeResponseDto response = new EmployeeResponseDto(
                1, "Mike",
                null, null, null, null, null, false);

        Employee employee = Employee.builder().id(1).name("Mike").build();

        when(employeeService.getById(1)).thenReturn(employee);
        when(employeeMapper.toEmployeeResponse(employee)).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mike"));

        verify(employeeService).getById(1);
    }

    @Test
    @DisplayName("PUT /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmployeeResponseDto_StatusOk_AfterUpdateEmployeeById() throws Exception {

        EmployeeResponseDto response = new EmployeeResponseDto(
                1,
                null, null, null, null, null, null, false);

        Employee employee = Employee.builder().id(1).build();

        when(employeeMapper.toEmployeeEntity(any(EmployeeUpdateRequestDto.class))).thenReturn(employee);
        when(employeeService.updateById(eq(1), any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toEmployeeResponse(any(Employee.class))).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(employeeService).updateById(eq(1), any(Employee.class));
    }

    @Test
    @DisplayName("PATCH /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnVoid_StatusNoContent_AfterSoftRemoveEmployeeById() throws Exception {

        doNothing().when(employeeService).removeSoftById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/users/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(employeeService).removeSoftById(1);
    }

    @Test
    @DisplayName("DELETE /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnVoid_StatusNoContent_AfterRemoveEmployeeById() throws Exception {

        doNothing().when(employeeService).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/users/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(employeeService).removeById(1);
    }

    @Test
    @DisplayName("GET /api/users/p")
    @WithMockUser(roles = "USER")
    void shouldPageEmployeeResponseDto_StatusOk_AfterGetAllEmployee() throws Exception {
        int page = 0;
        int size = 5;

        Employee employee1 = Employee.builder().id(1).name("John").country("US").build();
        Employee employee2 = Employee.builder().id(2).name("Jane").country("UK").build();

        List<Employee> employees = Arrays.asList(employee1, employee2);

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeesPage = new PageImpl<>(employees, pageable, employees.size());

        EmployeeResponseDto responseDto1 = new EmployeeResponseDto(
                1, "John", "US",
                null, null, null, null, false);

        EmployeeResponseDto responseDto2 = new EmployeeResponseDto(
                2, "Jane", "UK",
                null, null, null, null, false);

        when(employeeService.getAllWithPagination(pageable)).thenReturn(employeesPage);
        when(employeeMapper.toEmployeeResponse(employee1)).thenReturn(responseDto1);
        when(employeeMapper.toEmployeeResponse(employee2)).thenReturn(responseDto2);

        MockHttpServletRequestBuilder resultBuilder = MockMvcRequestBuilders.get("/api/users/p")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size));

        MvcResult result = mockMvc.perform(resultBuilder)
                .andExpect(status().isOk()).andReturn();

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);

        verify(employeeService, times(1)).getAllWithPagination(pageable);
        verify(employeeMapper, times(1)).toEmployeeResponse(employee1);
        verify(employeeMapper, times(1)).toEmployeeResponse(employee2);
    }

    @Test
    @DisplayName("GET /api/users/email/n")
    @WithMockUser(roles = "USER")
    void shouldReturnListOfEmployeeResponseDto_whenGetAllByEmailNull() throws Exception {

        Employee employee = Employee.builder().id(1).name("John").email("null").build();
        List<Employee> employees = List.of(employee);

        EmployeeResponseDto responseDto = new EmployeeResponseDto(
                1, "John",
                null, null, null, null, null, false);

        List<EmployeeResponseDto> expectedResponse = List.of(responseDto);

        when(employeeService.findAllByEmailNull()).thenReturn(employees);
        when(employeeMapper.toEmployeeResponseList(employees)).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/users/email/n"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(employeeService, times(1)).findAllByEmailNull();
        verify(employeeMapper, times(1)).toEmployeeResponseList(employees);
    }

    @Test
    @DisplayName("GET /api/users/country/lc")
    @WithMockUser(roles = "USER")
    void shouldReturnListOfEmployeeResponseDto_whenUpdateAllByCountryFirstCharLowerToUpper() throws Exception {

        Employee employee = Employee.builder().id(1).name("John").country("uS").build();
        List<Employee> employees = List.of(employee);

        EmployeeResponseDto responseDto1 = new EmployeeResponseDto(
                1, "John", "US",
                null, null, null, null, false);

        List<EmployeeResponseDto> expectedResponse = List.of(responseDto1);

        when(employeeService.updateAllByCountryFirstCharLowerToUpper()).thenReturn(employees);
        when(employeeMapper.toEmployeeResponseList(employees)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/country/lc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].country").value("US"));

        verify(employeeService, times(1)).updateAllByCountryFirstCharLowerToUpper();
        verify(employeeMapper, times(1)).toEmployeeResponseList(employees);
    }

    @Test
    @DisplayName("GET /api/users/country/notin")
    @WithMockUser(roles = "USER")
    void shouldReturnListOfEmployeeResponseDto_whenGetAllByCountryNotIn() throws Exception {

        Employee employee1 = Employee.builder().id(1).name("name1").country("A").build();
        List<Employee> employees = List.of(employee1);

        EmployeeResponseDto responseDto1 = new EmployeeResponseDto(
                1, "name1", "A",
                null, null, null, null, false);

        List<EmployeeResponseDto> expectedResponse = List.of(responseDto1);

        when(employeeService.findAllByCountryNotIn(anyList())).thenReturn(employees);
        when(employeeMapper.toEmployeeResponseList(employees)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/users/country/notin").param("countries", "А,В")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("name1"));

        verify(employeeService, times(1)).findAllByCountryNotIn(anyList());
        verify(employeeMapper, times(1)).toEmployeeResponseList(employees);
    }

    @Test
    @DisplayName("GET /api/users/deleted/ids")
    @WithMockUser(roles = "USER")
    void shouldReturnListOfEmployeeResponseDto_whenGetAllDeletedByIds() throws Exception {

        List<Employee> employees = new ArrayList<>();
        List<EmployeeResponseDto> expectedResponse = new ArrayList<>();

        when(employeeService.findAllDeletedByIds(anyList())).thenReturn(employees);
        when(employeeMapper.toEmployeeResponseList(employees)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/users/deleted/ids").param("ids", "1,2")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        verify(employeeService, times(1)).findAllDeletedByIds(anyList());
        verify(employeeMapper, times(1)).toEmployeeResponseList(employees);
    }
}
