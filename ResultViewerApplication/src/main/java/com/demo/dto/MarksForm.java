package com.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksForm {

    private String subjectName;
    private String marks;
    private String maxMarks;
    private String feedback;
    private String grade;

    private StudentForm student;
}
