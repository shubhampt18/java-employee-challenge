package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController implements IEmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Override
    public Object getAllEmployees() throws IOException {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Override
    public Object getEmployeesByNameSearch(@PathVariable("searchString") String searchString) {
        return new ResponseEntity<>(employeeService.getEmployeesByNameSearch(searchString), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return new ResponseEntity<>(employeeService.getHighestSalaryOfEmployees(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return new ResponseEntity<>(employeeService.getTopTenHighestEarningEmployeeNames(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        return new ResponseEntity<>(
                employeeService.createEmployee(
                        (String) employeeInput.get("name"), (String) employeeInput.get("salary"), (String)
                                employeeInput.get("age")),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
    }
}
