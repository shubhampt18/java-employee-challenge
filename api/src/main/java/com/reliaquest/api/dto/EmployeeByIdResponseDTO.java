package com.reliaquest.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeByIdResponseDTO {
    private String status;
    private Employee data;
}
