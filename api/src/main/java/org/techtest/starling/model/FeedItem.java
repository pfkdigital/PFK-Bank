package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.techtest.starling.enums.Direction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedItem {
    private UUID feedItemUid;
    private UUID categoryUid;
    private CurrencyAndAmount amount;
    private CurrencyAndAmount sourceAmount;
    private Direction direction;
    private Date updatedAt;
    private Date transactionTime;
    private Date settlementTime;
    private Date retryAllocationUntilTime;
    private String source;
    private String sourceSubType;
    private String status;
    private UUID transactingApplicationUserUid;
    private String counterPartyType;
    private UUID counterPartyUid;
    private String counterPartyName;
    private UUID counterPartySubEntityUid;
    private String counterPartySubEntityName;
    private String counterPartySubEntityIdentifier;
    private String counterPartySubEntitySubIdentifier;
    private Double exchangeRate;
    private BigDecimal totalFees;
    private CurrencyAndAmount totalFeeAmount;
    private String reference;
    private String country;
    private String spendingCategory;
    private String userNote;
    private RoundUp roundUp;
    private boolean hasAttachment;
    private boolean hasReceipt;
    private BatchPaymentDetails batchPaymentDetails;
}
