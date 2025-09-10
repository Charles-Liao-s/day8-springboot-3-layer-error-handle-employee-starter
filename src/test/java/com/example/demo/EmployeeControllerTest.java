package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.entity.Employee;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void cleanEmployees() throws Exception {
//        employeeController.empty();
        mockMvc.perform(delete("/employees"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_404_when_employee_not_found() throws Exception {
        mockMvc.perform(get("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_all_employee() throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(new Employee(null, "John Smith", 28, "MALE", 60000.0));
        String json2 = gson.toJson(new Employee(null, "Jane Doe", 22, "FEMALE", 60000.0));


        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2));


        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_return_employee_when_employee_found() throws Exception {
        Employee expect = new Employee(1, "John Smith", 28, "male", 60000.0);
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(1, "John Smith", 28, "male", 60000.0));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expect.getId()))
                .andExpect(jsonPath("$.name").value(expect.getName()))
                .andExpect(jsonPath("$.age").value(expect.getAge()))
                .andExpect(jsonPath("$.gender").value(expect.getGender()))
                .andExpect(jsonPath("$.salary").value(expect.getSalary()));
    }

    @Test
    void should_return_male_employee_when_employee_found() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(null, "John Smith", 28, "male", 60000.0));
        String json2 = gson.toJson(new Employee(null, "Jane Doe", 22, "female", 60000.0));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2));

        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Smith"));

    }

    @Test
    void should_create_employee() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(null, "John Smith", 28, "male", 60000.0));


        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    void should_return_200_with_empty_body_when_no_employee() throws Exception {
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void should_return_200_with_employee_list() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(null, "John Smith", 28, "male", 60000.0));
        Employee expect = new Employee(1, "John Smith", 28,"male", 60000.0);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(expect.getName()))
                .andExpect(jsonPath("$[0].age").value(expect.getAge()))
                .andExpect(jsonPath("$[0].gender").value(expect.getGender()))
                .andExpect(jsonPath("$[0].salary").value(expect.getSalary()));
    }

    @Test
    void should_status_204_when_delete_employee() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(1, "John Smith", 18, "male", 60000.0));
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(delete("/employees/" + 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_status_200_when_update_employee() throws Exception {

        Employee expect = new Employee(1, "John Smith", 28, "male", 60000.0);
        Gson gson = new Gson();
        String json = gson.toJson(new Employee(1, "John Smith", 28, "male", 60000.0));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));


        mockMvc.perform(put("/employees/" + expect.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expect.getId()))
                .andExpect(jsonPath("$.name").value(expect.getName()))
                .andExpect(jsonPath("$.age").value(expect.getAge()))
                .andExpect(jsonPath("$.gender").value(expect.getGender()))
                .andExpect(jsonPath("$.salary").value(expect.getSalary()));
    }

    @Test
    void should_status_200_and_return_paged_employee_list() throws Exception {
        Gson gson = new Gson();
        for (int i = 1; i <= 12; i++) {
            String json = gson.toJson(new Employee((Integer) null, "Employee" + i, 20 + i, i % 2 == 0 ? "male" : "female", 5000.0 + i));
            mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json));
        }


        mockMvc.perform(get("/employees?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }
}
