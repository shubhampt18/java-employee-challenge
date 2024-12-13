package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeList;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.utils.Constants;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmployeeService implements IEmployeeService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Employee> getAllEmployees() throws IOException {
        ResponseEntity<EmployeeList> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    new URI(Constants.GET_EMPLOYEE_URL), HttpMethod.GET, null, EmployeeList.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        log.info("Response of Request :{} ", responseEntity.getBody().getData());
        return responseEntity.getBody().getData();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = null;
        try {
            employeeList = getAllEmployees();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return employeeList.stream()
                .filter(employee -> employee.getEmployee_name().contains(searchString))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        ResponseEntity<EmployeeResponse> responseEntity =
                restTemplate.exchange(Constants.GET_EMPLOYEE_ID_URL, HttpMethod.GET, null, EmployeeResponse.class, id);

        log.info("Response of Request :{} ", responseEntity.getBody().getData());
        return responseEntity.getBody().getData();
    }

    public Employee getEmployeeByName(String employee_name) {
        ResponseEntity<EmployeeResponse> responseEntity =
                restTemplate.exchange(Constants.GET_EMPLOYEE_NAME_URL, HttpMethod.GET, null, EmployeeResponse.class, employee_name);

        log.info("Response of Request :{} ", responseEntity.getBody().getData());
        return responseEntity.getBody().getData();
    }

    public Integer getHighestSalaryOfEmployees() {
        List<Employee> employeeList = null;
        try {
            employeeList = getAllEmployees();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return employeeList.stream()
                .max(Comparator.comparing(Employee::getEmployee_salary))
                .get()
                .getEmployee_salary();
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employeeList = null;
        try {
            employeeList = getAllEmployees();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return employeeList.stream()
                .sorted(Comparator.comparing(Employee::getEmployee_salary).reversed())
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
    }

    public Employee createEmployee(String name, String salary, String age) {
        Employee employee = Employee.builder()
                .employee_name(name)
                .employee_salary(Integer.parseInt(salary))
                .employee_age(Integer.parseInt(age))
                .build();

        ResponseEntity<EmployeeResponse> employeeResponseResponseEntity = restTemplate.exchange(
                Constants.CREATE_EMPLOYEE_URL, HttpMethod.POST, new HttpEntity<>(employee), EmployeeResponse.class);

        log.info(
                "Response of Request :{} ",
                employeeResponseResponseEntity.getBody().getData());

        return employeeResponseResponseEntity.getBody().getData();
    }

    public String deleteEmployee(String id) {

        Employee employee = getEmployeeById(id);

        ResponseEntity<EmployeeResponse> employeeResponseResponseEntity = restTemplate.exchange(
                Constants.DELETE_EMPLOYEE_URL, HttpMethod.DELETE, null, EmployeeResponse.class, id);

        if (employeeResponseResponseEntity.getStatusCode() == HttpStatus.OK) return employee.getEmployee_name();

        return "Employee does not exist";
    }

    public String deleteEmployeeByName(String employee_name) {

        Employee employee = getEmployeeByName(employee_name);

        ResponseEntity<EmployeeResponse> employeeResponseResponseEntity = restTemplate.exchange(
                Constants.DELETE_EMPLOYEE_URL, HttpMethod.DELETE, null, EmployeeResponse.class, employee_name);

        if (employeeResponseResponseEntity.getStatusCode() == HttpStatus.OK) return employee.getEmployee_name();

        return "Employee does not exist";
    }
}
