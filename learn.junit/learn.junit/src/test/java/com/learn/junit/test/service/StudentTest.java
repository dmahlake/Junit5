package com.learn.junit.test.service;

import com.learn.junit.test.persistance.model.Student;
import com.learn.junit.test.persistance.repo.StudentRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentTest {


    @Autowired
    StudentService service;

    @MockBean
    StudentRepo repo;

    @Test
    public void addStudentTest()
    {
        Student student = new Student(1, "Dipuo", "Mahlake", "dede@gmail.com", "0607400172", 80);

        doReturn(student).when(repo).save(any());

        Student returnStud = service.createStudent(student);

        Assertions.assertNotNull(returnStud, "Student record should not be null");
        Assertions.assertEquals(1, returnStud.getId(), "Student record was created");
    }



    @Test
    public void deleteCustomerTest()
    {
        Student student = new Student();
        student.setId(4);
        student.setFirstName("Nomvula");
        student.setLastName("Sikhosana");
        student.setEmailAddress("sikhosana@gmail.com");
        student.setPhoneNumber("0762565589");
        student.setGrade(80);

        service.deleteStudent(student.getId());
        verify(repo,times(1)).deleteById(student.getId());
    }

    @Test
    public void getStudentByIdTest()
    {
        when(repo.findAll()).thenReturn(Stream.of(new Student(1, "Dipuo", "Mahlake", "dipuo@gmail.com", "0607400172", 80))
                .collect(Collectors.toList()));
        assertEquals(1, service.getAllStudent().size());//Compare by the size of user
    }

    @Test
    public void findStudentByIdTest()
    {
        Student student = new Student(1, "Dipuo", "Mahlake", "dipuo@gmail.com", "0607400172", 80);

        doReturn(Optional.of(student)).when(repo).findById(1);
        Optional<Student> returnStudent = Optional.ofNullable(service.getStudentById(1));
        Assertions.assertTrue(returnStudent.isPresent(), "Student was not found");
        Assertions.assertSame(returnStudent.get(), student, "The Student returned was not the same as the mock");


    }


}
