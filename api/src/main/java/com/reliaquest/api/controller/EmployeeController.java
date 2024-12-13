package com.reliaquest.api.controller;

import com.reliaquest.api.dto.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EmployeeController implements IEmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("EmployeeController|getAllEmployees|Entry");
        List<Employee> employeeList = new ArrayList<>();
        try {
            employeeList = employeeService.getAllEmployeeList();
        } catch (Exception e) {
            logger.error("EmployeeController|getAllEmployees|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.info("EmployeeController|getAllEmployees|Exit");

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {

        logger.info("EmployeeController|getEmployeesByNameSearch|Entry");
        List<Employee> employeeListByName = new ArrayList<>();
        try {
            employeeListByName = employeeService.getEmployeeBySearchName(searchString);
        } catch (Exception e) {
            logger.error("EmployeeController|getEmployeesByNameSearch|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.info("EmployeeController|getEmployeesByNameSearch|Exit");

        return new ResponseEntity<>(employeeListByName, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        logger.info("EmployeeController|getEmployeeById|Entry");

        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (Exception e) {
            logger.error("EmployeeController|getEmployeeById|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        logger.info("EmployeeController|getEmployeeById|Exit");

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("EmployeeController|getHighestSalaryOfEmployees|Entry");

        Integer maxSalary = null;
        try {
            maxSalary = employeeService.getHighestSalaryOfEmployee();

        } catch (Exception e) {
            logger.error("EmployeeController|getHighestSalaryOfEmployees|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.info("EmployeeController|getHighestSalaryOfEmployees|Exit");

        return new ResponseEntity<>(maxSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("EmployeeController|getTopTenHighestEarningEmployeeNames|Entry");

        List<String> topTenHighestEarningEmpNamesList = new ArrayList<>();
        try {
            topTenHighestEarningEmpNamesList = employeeService.getTopTenHighestEarningEmployeeNames();

        } catch (Exception e) {
            logger.error("EmployeeController|getTopTenHighestEarningEmployeeNames|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        logger.info("EmployeeController|getTopTenHighestEarningEmployeeNames|Exit");

        return new ResponseEntity<>(topTenHighestEarningEmpNamesList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createEmployee(Object employeeInput) {
        return null;
    }

    public ResponseEntity<Object> createEmployee(Map<String, Object> employeeInput) {
        logger.info("EmployeeController|createEmployee|Entry");

        Object response = null;
        try {
            response = employeeService.createEmployee(employeeInput);
        } catch (Exception e) {
            logger.error("EmployeeController|createEmployee|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.info("EmployeeController|createEmployee|Employee Created Successfully|Exit");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteEmployeeById(String id) {
        logger.info("EmployeeController|deleteEmployeeById|Entry");

        Object response = null;
        try {
            response = employeeService.deleteEmployee(id);

        } catch (Exception e) {
            logger.error("EmployeeController|createEmployee|Error:{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.info("EmployeeController|deleteEmployeeById|Employee Deleted Successfully|Exit");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
