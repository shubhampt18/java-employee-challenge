package com.reliaquest.api.service.impl;

import com.reliaquest.api.dto.Employee;
import com.reliaquest.api.dto.EmployeeByIdResponseDTO;
import com.reliaquest.api.dto.EmployeeListResponseDTO;
import com.reliaquest.api.service.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public WebClient webClient;

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<Employee> getAllEmployeeList() {
        logger.debug("EmployeeService|getAllEmployeeList|Entry");
        Mono<EmployeeListResponseDTO> employeeMono =
                webClient.get().uri("/employees").retrieve().bodyToMono(EmployeeListResponseDTO.class);

        logger.debug("EmployeeService|getAllEmployeeList|Exit");
        return employeeMono.block().getData();
    }

    @Override
    public Employee getEmployeeById(String id) {
        logger.debug("EmployeeService|getEmployeeById|Entry");

        Mono<EmployeeByIdResponseDTO> employeeMono =
                webClient.get().uri("/employee/" + id).retrieve().bodyToMono(EmployeeByIdResponseDTO.class);

        logger.debug("EmployeeService|getEmployeeById|Exit");

        return employeeMono.block().getData();
    }

    @Override
    public List<Employee> getEmployeeBySearchName(String searchString) {
        logger.debug("EmployeeService|getEmployeeBySearchName|Entry");

        List<Employee> employeeList = getAllEmployeeList();

        logger.debug("EmployeeService|getEmployeeBySearchName|Exit");

        return employeeList.stream()
                .filter(emp -> emp.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getHighestSalaryOfEmployee() {
        logger.debug("EmployeeService|getHighestSalaryOfEmployee|Entry");

        List<Employee> employeeList = getAllEmployeeList();
        logger.debug("EmployeeService|getHighestSalaryOfEmployee|Exit");

        return employeeList.stream()
                .map(emp -> emp.getEmployeeSalary())
                .mapToInt(Integer::parseInt)
                .max()
                .getAsInt();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Entry");

        List<Employee> employeeList = getAllEmployeeList();

        logger.debug("EmployeeService|getTopTenHighestEarningEmployeeNames|Exit");

        return employeeList.stream()
                .sorted()
                .map(emp -> emp.getEmployeeName())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public Object createEmployee(Map<String, Object> employeeInput) {
        logger.debug("EmployeeService|createEmployee|Entry");

        Object employeeMono = webClient
                .post()
                .uri("/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(employeeInput))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        logger.debug("EmployeeService|createEmployee|Exit");

        return employeeMono;
    }

    @Override
    public Object deleteEmployee(String id) {
        logger.debug("EmployeeService|deleteEmployee|Entry");

        Object employeeMono = webClient
                .delete()
                .uri("/delete/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        logger.debug("EmployeeService|deleteEmployee|Exit");

        return employeeMono;
    }
}
