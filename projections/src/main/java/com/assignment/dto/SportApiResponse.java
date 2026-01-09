package com.assignment.dto;

import lombok.Data;

import java.util.List;

@Data
public class SportApiResponse {
    private String status;
    private String msg;
    private String err;
    private List<SportApiDTO> data;
}
