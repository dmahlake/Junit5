package com.learn.junit.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.junit.test.persistance.model.Student;
import com.learn.junit.test.persistance.repo.StudentRepo;
import com.learn.junit.test.rest.StudentController;
import com.learn.junit.test.service.StudentService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {


    @MockBean
    StudentService service;

    @MockBean
    StudentRepo repo;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    StudentController controller;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
  public void DeleteStudent() throws  Exception {

        Student student = new Student();
        student.setId(4);
        student.setFirstName("Nomvula");
        student.setLastName("Sikhosana");
        student.setEmailAddress("sikhosana@gmail.com");
        student.setPhoneNumber("0762565589");
        student.setGrade(80);

        Mockito.when(repo.findById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/student/delete/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void TestAddStudent() throws Exception {

        Student student = new Student();
        student.setId(4);
        student.setFirstName("Nomvula");
        student.setLastName("Sikhosana");
        student.setEmailAddress("sikhosana@gmail.com");
        student.setPhoneNumber("0762565589");
        student.setGrade(80);


        when(service.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/add")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Nomvula"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Sikhosana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("sikhosana@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("0762565589"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value(80));
    }

    @Test
    public void testGetStudentById() throws Exception
    {
        Student student = new Student();

        student.setFirstName("Nomvula");
        student.setLastName("Sikhosana");
        student.setEmailAddress("sikhosana@gmail.com");
        student.setPhoneNumber("0762565589");
        student.setGrade(80);

        when(service.getStudentById(anyInt())).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/find/4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Nomvula"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Sikhosana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("sikhosana@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("0762565589"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value(80))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateStudent() throws Exception {

        String uri = "/api/v1/student/update/4";

        Student student = new Student( 4,"Nomvula", "Sikhosana", "sikhosana@gmail.com", "0762565589", 80);
        student.setGrade(85);
        String inputJson = mapper.writeValueAsString(student);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
    }

}
