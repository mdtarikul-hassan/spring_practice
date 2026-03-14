package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentForm {

    
    private String name;

    private String rollNumber;


    private String email;

    private String address;

    private String schoolName;
    private String photoName;
    //    @NotBlank(message = "Dob is required !!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String standard;

    private String fatherName;

    private String gender;
}
