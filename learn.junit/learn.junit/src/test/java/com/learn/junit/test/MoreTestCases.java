package com.learn.junit.test;

import com.learn.junit.test.persistance.model.Student;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MoreTestCases {

    private Student student;
    private Student spyStudent;

    private  static final String firstName = "Dipuo";
    private  static final String lastName = "Mahlake";
    private  static final String emailAddress = "dede@gmail.com";
    private  static final String phoneNumber = "0607400172";

    @BeforeMethod
    public void spyMethod()
    {
        student = new Student(firstName, lastName, emailAddress, phoneNumber);
        spyStudent = spy(student);
    }

    @Test
    public void TestStudentDetails()
    {
        spyStudent.setFirstName("Nomvula");
        spyStudent.setLastName("Sikhosana");
        spyStudent.setPhoneNumber("0137950667");

        assertFalse(student.getFirstName() == spyStudent.getFirstName());
        assertFalse(student.getLastName() == spyStudent.getLastName());
        assertFalse(student.getPhoneNumber() == spyStudent.getPhoneNumber());
    }


    @Test
    public void TestVerifyStudent()
    {
        System.out.println("First name:" + spyStudent.getFirstName());
        System.out.println("Phone Number:" + spyStudent.getPhoneNumber());
        System.out.println("Last Name:" + spyStudent.getLastName());


        InOrder inOrder = inOrder(spyStudent);

        System.out.println("Verify getFirstName() is called");
        inOrder.verify(spyStudent).getFirstName();

        System.out.println("Verify getLastName() is called");
        inOrder.verify(spyStudent).getLastName();

        System.out.println("Verify Student phone number is called");
        verify(spyStudent).getPhoneNumber();

        assertEquals(spyStudent.getFirstName(), firstName);
        assertEquals(spyStudent.getLastName(), lastName);
        assertEquals(spyStudent.getPhoneNumber(), phoneNumber);

        System.out.println("Verify firstName called twice");
        verify(spyStudent, times(2)).getFirstName();
    }

    @Test
    public void TestSpyList() {
        List<String> list = new ArrayList<>();
        List<String> spyOnList = spy(list);

        spyOnList.add("A");
        assertEquals(1, spyOnList.size());
        assertEquals("A", spyOnList.get(0));

        spyOnList.add("E");
        assertEquals(2, spyOnList.size());
        assertEquals("E", spyOnList.get(1));

        when(spyOnList.size()).thenReturn(10);
        assertEquals(10, spyOnList.size());
    }

}
