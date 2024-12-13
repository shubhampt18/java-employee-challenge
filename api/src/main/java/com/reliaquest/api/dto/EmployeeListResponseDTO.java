package com.reliaquest.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeListResponseDTO {

    private String status;
    private List<Employee> data;
}
