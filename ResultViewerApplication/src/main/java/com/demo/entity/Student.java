package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "result_viewer_students")
public class Student {
    @Id
    private String id;
    private String name;
    private String rollNumber;
    private String email;
    @Column(length = 1000)
    private String address;
    private String schoolName;
    private String photoName;
    private LocalDate dateOfBirth;
    private String standard;
    private String fatherName;
    private String gender;
    // table field according to project:

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks = new ArrayList<>();

}
