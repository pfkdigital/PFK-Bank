package org.techtest.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.techtest.starling.model.*;
import org.techtest.starling.service.SavingsGoalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(SavingsGoalController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SavingsGoalControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SavingsGoalService savingsGoalService;

    private UUID accountUid;
    private UUID savingsGoalUid;

    @BeforeEach
    void setUp() {
        accountUid = UUID.randomUUID();
        savingsGoalUid = UUID.randomUUID();
    }

    @DisplayName("Should return all savings goals for an account")
    @Test
    void shouldReturnAllSavingsGoalsForAnAccount() throws Exception {
        //Arrange
        SavingGoalV2 savingGoalV2 = SavingGoalV2
                .builder()
                .savingsGoalUid(savingsGoalUid)
                .name("Test Savings Goal")
                .target(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(1000)).build())
                .totalSaved(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(500)).build())
                .savedPercentage(50)
                .build();
        SavingGoalsV2 savingGoalsV2 = new SavingGoalsV2();
        savingGoalsV2.setSavingsGoalList(List.of(savingGoalV2));

        when(savingsGoalService.getSavingsGoals(accountUid)).thenReturn(savingGoalsV2);

        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/savings-goals/account/" + accountUid));

        //Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(savingGoalsV2), resultActions.andReturn().getResponse().getContentAsString());

        verify(savingsGoalService).getSavingsGoals(accountUid);
    }

    @DisplayName("Should create a new savings goal for an account")
    @Test
    void shouldCreateANewSavingsGoalForAnAccount() throws Exception {
        //Arrange
        SavingsGoalRequestV2 savingsGoalRequestV2 = SavingsGoalRequestV2
                .builder()
                .name("Test Savings Goal")
                .currency("GBP")
                .target(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(1000)).build())
                .build();

        CreateOrUpdateSavingsGoalResponseV2 createOrUpdateSavingsGoalResponseV2 = CreateOrUpdateSavingsGoalResponseV2
                .builder()
                .savingsGoalUid(savingsGoalUid)
                .success(true)
                .build();

        when(savingsGoalService.createNewSavingsGoals(accountUid, savingsGoalRequestV2)).thenReturn(createOrUpdateSavingsGoalResponseV2);

        //Act
        ResultActions resultActions = mockMvc.perform(
                put("/api/v1/savings-goals/account/" + accountUid)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savingsGoalRequestV2))
        );

        //Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(createOrUpdateSavingsGoalResponseV2), resultActions.andReturn().getResponse().getContentAsString());

        verify(savingsGoalService).createNewSavingsGoals(accountUid, savingsGoalRequestV2);
    }

    @DisplayName("Should transfer money to a savings goal")
    @Test
    void shouldTransferMoneyToASavingsGoal() throws Exception {
        //Arrange
        TopUpRequestV2 topUpRequestV2 = TopUpRequestV2
                .builder()
                .amount(CurrencyAndAmount.builder().currency("GBP").minorUnits(BigDecimal.valueOf(500)).build())
                .build();

        SavingsGoalTransferResponseV2 savingsGoalTransferResponseV2 = SavingsGoalTransferResponseV2
                .builder()
                .success(true)
                .build();

        when(savingsGoalService.transferMoneyToSavingsGoal(topUpRequestV2, accountUid, savingsGoalUid)).thenReturn(savingsGoalTransferResponseV2);

        //Act
        ResultActions resultActions = mockMvc.perform(
                put("/api/v1/savings-goals/account/" + accountUid + "/savings-goal/" + savingsGoalUid)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(topUpRequestV2))
        );

        //Assert
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(savingsGoalTransferResponseV2), resultActions.andReturn().getResponse().getContentAsString());

        verify(savingsGoalService).transferMoneyToSavingsGoal(topUpRequestV2, accountUid, savingsGoalUid);
    }
}