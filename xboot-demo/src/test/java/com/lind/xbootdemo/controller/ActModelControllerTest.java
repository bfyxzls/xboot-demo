package com.lind.xbootdemo.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActModelControllerTest extends BaseControllerTest {
    @Test
    public void getModel() throws Exception {
        this.mockMvc.perform(
                get("/act/add"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(
                get("/act/get"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
