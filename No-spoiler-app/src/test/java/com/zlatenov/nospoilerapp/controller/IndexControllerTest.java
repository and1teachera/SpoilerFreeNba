package com.zlatenov.nospoilerapp.controller;

import com.zlatenov.nospoilerapp.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Angel Zlatenov
 */

public class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    private final GameService gamesService;


    public IndexControllerTest() {
        gamesService = Mockito.mock(GameService.class);
    }

    @BeforeEach
    public void setup() {
        indexController = new IndexController(gamesService);
    }

    @Disabled
    @Test
    public void index() throws Exception {
        MockMvcBuilders.standaloneSetup(indexController)
                .build()
                .perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));

    }
}
