package com.demo.controller;

import com.demo.dto.StudentForm;
import com.demo.entity.Mark;
import com.demo.entity.Student;
import com.demo.repo.StudentRepo;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ModelMapper modelMapper;
    private StudentRepo studentRepo;

    public AdminController(ModelMapper modelMapper, StudentRepo studentRepo) {
        this.modelMapper = modelMapper;
        this.studentRepo = studentRepo;
    }

    @GetMapping("/add-result")
    public String addResultForm(Model model) {
        StudentForm studentForm = new StudentForm();

        List<String> standardOptions = new ArrayList<>();
        standardOptions.add("CLASS 1");
        standardOptions.add("CLASS 2");
        standardOptions.add("CLASS 3");
        standardOptions.add("CLASS 4");
        standardOptions.add("CLASS 5");
        standardOptions.add("CLASS 6");


        model.addAttribute("studentForm", studentForm);
        model.addAttribute("standardOptions", standardOptions);
        return "admin/add_result";
    }

    @PostMapping(value = "/add-result-action")
    public String processAddResultForm(
            @Valid @ModelAttribute StudentForm studentForm,
            BindingResult bindingResult,
            Model model
    ) {


        if (bindingResult.hasErrors()) {
            List<String> standardOptions = new ArrayList<>();
            standardOptions.add("CLASS 1");
            standardOptions.add("CLASS 2");
            standardOptions.add("CLASS 3");
            standardOptions.add("CLASS 4");
            standardOptions.add("CLASS 5");
            standardOptions.add("CLASS 6");
            model.addAttribute("standardOptions", standardOptions);
            return "admin/add_result";
        }


//        convert student form to student entity

        Student student = modelMapper.map(studentForm, Student.class);

        //har marks to attach student
        List<Mark> updatedList = student.getMarks().stream().map(mark -> {
            mark.setStudent(student);
            return mark;
        }).toList();

        //update student list
        student.setMarks(updatedList);


        student.setId(UUID.randomUUID().toString());
        studentRepo.save(student);
        return "redirect:/admin/add-result?message=Student added successfully ";

    }
}
