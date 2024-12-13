package com.reliaquest.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Employee implements Comparable<Employee> {

    private String id;
    private String employeeName;
    private String employeeSalary;
    private String employeeAge;
    private String profileImage;

    @Override
    public int compareTo(Employee o) {

        return Integer.parseInt(o.employeeSalary) - Integer.parseInt(this.employeeSalary);
    }
}
