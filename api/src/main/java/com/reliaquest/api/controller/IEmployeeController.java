package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Please <b>do not</b> modify this interface. If you believe there's a bug or the API contract does not align with our
 * mock web server... that is intentional. Good luck!
 *
 * @implNote It's uncommon to have a web controller implement an interface; We include such design pattern to
 * ensure users are following the desired input/output for our API contract, as outlined in the code assessment's README.
 *
 */
@RestController
public interface IEmployeeController {

    @GetMapping()
    Object getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    Object getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);
}
