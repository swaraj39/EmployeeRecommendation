package com.example.demo.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EMPReommendDTO {
    private Long rank;
    private Long empid;
    private String empname;
    private Long score;
    private String explanation;
}
