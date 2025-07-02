package com.kifiya.payment.processing.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO for submitting a new payment request")
public class PaymentRequestDto {

    @NotBlank(message = "Customer ID is required")
    @Schema(description = "Unique identifier for the customer", example = "cust_12345")
    private String customerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Schema(description = "Amount of the payment", example = "100.50")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required")
    @Schema(description = "Type of payment method (e.g., 'credit_card', 'telebir')", example = "credit_card")
    private String paymentMethod;

    @NotBlank(message = "Payment provider is required")
    @Schema(description = "Preferred payment provider (e.g., 'cbe', 'telebir', 'yaya')", example = "stripe")
    private String paymentProvider;
}