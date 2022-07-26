package com.learn.junit.test.service;

import com.learn.junit.test.persistance.model.Student;
import com.learn.junit.test.persistance.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public Student createStudent(Student request)
    {
        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmailAddress(request.getEmailAddress());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setGrade(request.getGrade());
        return studentRepo.save(student);
    }

    public void deleteStudent(int id)
    {
        studentRepo.deleteById(id);
    }

    public Student updateStudent(Student request, int id)
    {
        Student updateStudent = studentRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No student record found  id " + id));
        updateStudent.setFirstName(request.getFirstName());
        updateStudent.setLastName(request.getLastName());
        updateStudent.setEmailAddress(request.getEmailAddress());
        updateStudent.setPhoneNumber(request.getPhoneNumber());
        updateStudent.setGrade(request.getGrade());

        return studentRepo.save(updateStudent);
    }

    public Student getStudentById(int id)
    {
        return studentRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Student not found for id " + id));

    }

    public List<Student>getAllStudent()
    {
        return studentRepo.findAll();
    }
}
