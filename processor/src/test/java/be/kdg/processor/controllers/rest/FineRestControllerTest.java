package be.kdg.processor.controllers.rest;

import be.kdg.processor.dto.FineDTO;
import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FineRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFine() throws Exception {
        FineDTO fineDTO = new FineDTO();
        String requestJson = objectMapper.writeValueAsString(fineDTO);

        mockMvc.perform(get("api/fines/1")
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());
    }
}