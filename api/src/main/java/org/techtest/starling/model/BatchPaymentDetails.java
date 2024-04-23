package org.techtest.starling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchPaymentDetails {
    private UUID batchPaymentUid;
    private String batchPaymentType;
}
