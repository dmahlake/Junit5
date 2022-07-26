package com.learn.junit.test.rest;

import com.learn.junit.test.persistance.model.Student;
import com.learn.junit.test.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student request)
    {
            return ResponseEntity.ok( service.createStudent(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody Student request, @PathVariable(value = "id")int id)
    {
            return ResponseEntity.ok(service.updateStudent(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id)
    {
            service.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable int id)
    {
            return new ResponseEntity<>(service.getStudentById(id), HttpStatus.OK);

    }

}
