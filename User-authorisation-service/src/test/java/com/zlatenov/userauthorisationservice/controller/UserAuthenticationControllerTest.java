package com.zlatenov.userauthorisationservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Angel Zlatenov
 */
//@ExtendWith(SpringExtension.class) //not required
@WebMvcTest(controllers = UserAuthenticationController.class)
public class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginUser() throws Exception {
        mockMvc.perform(post("/login")
                                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUser() throws Exception {
        mockMvc.perform(post("/register")
                                .contentType("application/json"))
                .andExpect(status().isOk());
    }

}