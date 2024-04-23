package org.techtest.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.techtest.starling.model.RoundUpRequest;
import org.techtest.starling.model.RoundUpResponse;
import org.techtest.starling.model.SavingsGoalTransferResponseV2;
import org.techtest.starling.service.RoundingUpService;


import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(RoundUpController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RoundUpControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoundingUpService roundingUpService;

    @Test
    @DisplayName("Should return the total amount rounded up")
    void shouldReturnTotalAmountRoundUp() throws Exception {
        //Arrange
        RoundUpRequest roundUpRequest = RoundUpRequest.builder()
                .accountUid(UUID.randomUUID())
                .savingsGoalUid(UUID.randomUUID())
                .minTransactionTimestamp("2024-04-02T00:00:00.000Z")
                .maxTransactionTimestamp("2024-04-09T00:00:00.000Z")
                .build();

        SavingsGoalTransferResponseV2 savingsGoalTransferResponseV2 = SavingsGoalTransferResponseV2.builder()
                .transferUid(UUID.randomUUID())
                .success(true)
                .build();

        RoundUpResponse roundUpResponse = RoundUpResponse.builder()
                .transferResponseV2(savingsGoalTransferResponseV2)
                .roundUpMessage("Round up successful")
                .build();

        when(roundingUpService.roundUp(roundUpRequest)).thenReturn(roundUpResponse);

        //Act
        ResultActions resultActions = mockMvc.perform(put("/api/v1/roundup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roundUpRequest)));

        //Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(resultActions.andReturn().getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(resultActions.andReturn().getResponse().getContentAsString(), objectMapper.writeValueAsString(roundUpResponse));

        verify(roundingUpService).roundUp(roundUpRequest);
    }
}