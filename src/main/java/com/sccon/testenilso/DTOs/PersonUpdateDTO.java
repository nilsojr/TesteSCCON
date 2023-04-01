package com.sccon.testenilso.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateDTO {
    private String name;
    private LocalDate birthDate;
    private LocalDate admissionDate;
    private Double salary;
}
