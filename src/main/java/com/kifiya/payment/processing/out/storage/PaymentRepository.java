package com.kifiya.payment.processing.out.storage;

import com.kifiya.payment.processing.core.entities.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends CrudRepository<Payment,Long> , PagingAndSortingRepository<Payment,Long> {
}
