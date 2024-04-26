package org.techtest.starling.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.techtest.starling.exception.ApiRuntimeException;
import org.techtest.starling.model.*;

import java.util.UUID;

// This class is used to interact with the Starling savings goal API endpoints
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SavingsGoalClient {
    private final BaseHttpClient baseHttpClient;

    @Value("${starling.savings-goals.all-savings-goals.endpoint}")
    private String savingGoalsUrl;

    @Value("${starling.savings-goals.create-savings-goal.endpoint}")
    private String createSavingsGoalUrl;

    @Value("${starling.savings-goals.transfer-money-to-savings-goal.endpoint}")
    private String savingsGoalTransferUrl;

    /**
     * Get all savings goals
     *
     * @param accountUid accountUid
     * @return SavingGoalsV2
     */
    public SavingGoalsV2 getSavingsGoals(UUID accountUid) {
        log.info("Getting all savings goals");
        return baseHttpClient
                .getRestClient()
                .get()
                .uri(savingGoalsUrl, accountUid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error while fetching all transactions between two dates");
                    throw new ApiRuntimeException(response,"Error while fetching all transactions between two dates");
                })
                .body(SavingGoalsV2.class);
    }

    /**
     * Transfer money to savings goal
     *
     * @param accountUid     accountUid
     * @param savingsGoalUid savingsGoalUid
     * @param transferUid    transferUid
     * @return SavingsGoalTransferResponseV2
     */
    public SavingsGoalTransferResponseV2 transferMoneyToSavingsGoal(TopUpRequestV2 topUpRequestV2, UUID accountUid, UUID savingsGoalUid, UUID transferUid) {
        log.info("Transferring money to savings goal");
        return baseHttpClient
                .getRestClient()
                .put()
                .uri(savingsGoalTransferUrl, accountUid, savingsGoalUid, transferUid)
                .accept(MediaType.APPLICATION_JSON)
                .body(topUpRequestV2)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error while transferring money to savings goal");
                    throw new ApiRuntimeException(response,"Error while transferring money to savings goal");
                })
                .body(SavingsGoalTransferResponseV2.class);
    }

    /**
     * Create a new savings goal
     *
     * @param accountUid           accountUid
     * @param savingsGoalRequestV2 savingsGoalRequestV2
     * @return CreateOrUpdateSavingsGoalResponseV2
     */
    public CreateOrUpdateSavingsGoalResponseV2 createNewSavingsGoals(UUID accountUid, SavingsGoalRequestV2 savingsGoalRequestV2) {
        log.info("Creating a new savings goal");
        return baseHttpClient
                .getRestClient()
                .put()
                .uri(createSavingsGoalUrl, accountUid)
                .accept(MediaType.APPLICATION_JSON)
                .body(savingsGoalRequestV2)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error while creating new savings goals");
                    throw new ApiRuntimeException(response,"Error while creating new savings goals");
                })
                .body(CreateOrUpdateSavingsGoalResponseV2.class);
    }
}
