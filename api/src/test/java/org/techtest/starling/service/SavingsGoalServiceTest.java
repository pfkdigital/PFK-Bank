package org.techtest.starling.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techtest.starling.client.SavingsGoalClient;
import org.techtest.starling.exception.SavingGoalNotFoundException;
import org.techtest.starling.model.*;
import org.techtest.starling.service.impl.SavingsGoalServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavingsGoalServiceTest {
    @Mock
    private SavingsGoalClient savingsGoalClient;

    @InjectMocks
    private SavingsGoalServiceImpl savingsGoalService;

    @Test
    @DisplayName("Test get all savings goals")
    public void testGetAllSavingsGoals() {
        // Arrange
        SavingGoalV2 testSavingGoal = new SavingGoalV2();
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();

        testSavingGoal.setSavingsGoalUid(savingsGoalUid);
        testSavingGoal.setName("Test Savings Goal");
        testSavingGoal.setTarget(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)));

        SavingGoalsV2 testSavingGoals = new SavingGoalsV2();
        testSavingGoals.setSavingsGoalList(List.of(testSavingGoal));

        when(savingsGoalClient.getSavingsGoals(accountUid)).thenReturn(testSavingGoals);

        // Act
        SavingGoalsV2 savingGoals = savingsGoalService.getSavingsGoals(accountUid);

        // Assert
        assertNotNull(savingGoals);
        assertEquals(1, savingGoals.getSavingsGoalList().size());
        assertEquals(savingsGoalUid, savingGoals.getSavingsGoalList().get(0).getSavingsGoalUid());

        verify(savingsGoalClient).getSavingsGoals(accountUid);
    }

    @Test
    @DisplayName("Test get single savings goal")
    public void testGetSingleSavingsGoal() {
        // Arrange
        SavingGoalV2 testSavingGoal = new SavingGoalV2();
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();

        testSavingGoal.setSavingsGoalUid(savingsGoalUid);
        testSavingGoal.setName("Test Savings Goal");
        testSavingGoal.setTarget(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)));

        SavingGoalsV2 testSavingGoals = new SavingGoalsV2();
        testSavingGoals.setSavingsGoalList(List.of(testSavingGoal));

        when(savingsGoalClient.getSavingsGoals(accountUid)).thenReturn(testSavingGoals);

        // Act
        SavingGoalV2 savingGoal = savingsGoalService.getSingleSavingsGoal(accountUid, savingsGoalUid);

        // Assert
        assertNotNull(savingGoal);
        assertEquals(savingsGoalUid, savingGoal.getSavingsGoalUid());

        verify(savingsGoalClient).getSavingsGoals(accountUid);
    }

    @Test
    @DisplayName("Test get a single savings goal throws SavingGoalNotFoundException")
    public void testGetSingleSavingsGoalThrowsSavingGoalNotFoundException() {
        // Arrange
        SavingGoalV2 testSavingGoal = new SavingGoalV2();
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();

        testSavingGoal.setSavingsGoalUid(savingsGoalUid);
        testSavingGoal.setName("Test Savings Goal");
        testSavingGoal.setTarget(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)));

        SavingGoalsV2 testSavingGoals = new SavingGoalsV2();
        testSavingGoals.setSavingsGoalList(List.of(testSavingGoal));

        when(savingsGoalClient.getSavingsGoals(accountUid)).thenReturn(testSavingGoals);

        // Act and Assert
        assertThrows(SavingGoalNotFoundException.class, () -> savingsGoalService.getSingleSavingsGoal(accountUid, UUID.randomUUID()));

        verify(savingsGoalClient).getSavingsGoals(accountUid);
    }

    @Test
    @DisplayName("Test transfer money to savings goal")
    public void testTransferMoneyToSavingsGoal() {
        // Arrange
        TopUpRequestV2 topUpRequest = new TopUpRequestV2(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)));
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();
        UUID transferUid = UUID.randomUUID();

        SavingsGoalTransferResponseV2 savingsGoalTransferResponseV2 = new SavingsGoalTransferResponseV2(transferUid, true);

        when(savingsGoalClient.transferMoneyToSavingsGoal(any(TopUpRequestV2.class), any(UUID.class), any(UUID.class), any(UUID.class))).thenReturn(savingsGoalTransferResponseV2);

        // Act
        SavingsGoalTransferResponseV2 response = savingsGoalService.transferMoneyToSavingsGoal(topUpRequest, accountUid, savingsGoalUid);

        // Assert
        assertNotNull(response);
        assertEquals(transferUid, response.getTransferUid());

        verify(savingsGoalClient).transferMoneyToSavingsGoal(any(TopUpRequestV2.class), any(UUID.class), any(UUID.class), any(UUID.class));
    }

    @Test
    @DisplayName("Test create new savings goal")
    public void testCreateNewSavingsGoal() {
        // Arrange
        SavingGoalV2 testSavingGoal = new SavingGoalV2();
        UUID accountUid = UUID.randomUUID();
        UUID savingsGoalUid = UUID.randomUUID();

        testSavingGoal.setSavingsGoalUid(savingsGoalUid);
        testSavingGoal.setName("Test Savings Goal");
        testSavingGoal.setTarget(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)));
        SavingsGoalRequestV2 savingsGoalRequestV2 = SavingsGoalRequestV2.builder()
                .name("Test Savings Goal")
                .currency("GBP")
                .target(new CurrencyAndAmount("GBP", BigDecimal.valueOf(1000)))
                .build();
        CreateOrUpdateSavingsGoalResponseV2 createOrUpdateSavingsGoalResponseV2 = new CreateOrUpdateSavingsGoalResponseV2(savingsGoalUid,true);

        when(savingsGoalClient.createNewSavingsGoals(accountUid, savingsGoalRequestV2)).thenReturn(createOrUpdateSavingsGoalResponseV2);

        // Act
        CreateOrUpdateSavingsGoalResponseV2 response = savingsGoalService.createNewSavingsGoals(accountUid, savingsGoalRequestV2);

        // Assert
        assertNotNull(response);
        assertEquals(savingsGoalUid, response.getSavingsGoalUid());

        verify(savingsGoalClient).createNewSavingsGoals(accountUid, savingsGoalRequestV2);
    }


}
