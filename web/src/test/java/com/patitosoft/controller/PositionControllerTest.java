package com.patitosoft.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patitosoft.api.PositionApi;
import com.patitosoft.dto.PositionDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PositionController.class)
public class PositionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PositionApi positionApi;

    @Test
    @Disabled
    void createPosition_ThrowBadRequest_IfPositionIdNull() throws Exception {
        PositionDTO positionDTO = new PositionDTO();
        String body = objectMapper.writeValueAsString(positionDTO);

        mvc.perform(post("/positions")
                .contentType("application/json")
                .content(body))
            .andExpect(status().isBadRequest());
    }
}