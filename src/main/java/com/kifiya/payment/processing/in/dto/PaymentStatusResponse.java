package com.kifiya.payment.processing.in.dto;

import com.kifiya.payment.dto.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentStatusResponse extends Response<List<Error>, PaymentOrder> {
   private List<Error> errors;
   private PaymentOrder paymentDto;
}
