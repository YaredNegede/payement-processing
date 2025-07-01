package com.kifiya.payment.processing.in;

import com.kifiya.payment.processing.core.PaymentProcessing;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.processing.in.dto.PaymentStatusResponse;
import com.kifiya.payment.exceptions.DuplicatePaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/processing")
public class PaymentProcessingController {

    private final PaymentProcessing paymentProcessing;

    /**
     *
     * @param paymentProcessing
     */
    public PaymentProcessingController(final PaymentProcessing paymentProcessing) {
        this.paymentProcessing = paymentProcessing;
    }

    /**
     * Endpoint to ingest a new payment order.
     * This fulfills requirement 1: "Ingest Payment Orders".
     *
     * @param paymentOrder The payment order details from the client.
     * @return A ResponseEntity indicating success or failure, possibly with the processed payment ID.
     */
    @PostMapping("/orders")
    public ResponseEntity<String> ingestPaymentOrder(@RequestBody PaymentOrder paymentOrder) {
        try {
            String paymentId = paymentProcessing.ingestPayment(paymentOrder);
            return new ResponseEntity<>("Payment order " + paymentId + " accepted for processing.", HttpStatus.ACCEPTED);
        } catch (DuplicatePaymentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to ingest payment order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to query the status of a specific payment.
     * This fulfills requirement 6: "Expose Payment Status".
     *
     * @param paymentId The unique identifier of the payment.
     * @return A ResponseEntity with the payment status or not found.
     */
    @GetMapping("/orders/{paymentId}/status")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable String paymentId) {
        PaymentStatusResponse status = paymentProcessing.getPaymentStatus(paymentId);
        if (status != null) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Optional: Endpoint to retrieve details of a specific payment order.
     * Useful for auditing or detailed client queries, but not explicitly in core requirements.
     *
     * @param paymentId The unique identifier of the payment.
     * @return A ResponseEntity with the payment order details or not found.
     */
    @GetMapping("/orders/{paymentId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderDetails(@PathVariable String paymentId) {
        PaymentOrder order = paymentProcessing.getPaymentOrder(paymentId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Optional: Endpoint to retrieve a list of all payment orders (potentially paginated).
     *
     * @return A ResponseEntity with a list of payment orders.
     */
    @GetMapping("/orders")
    public ResponseEntity<List<PaymentOrder>> getAllPaymentOrders() {
        List<PaymentOrder> orders = paymentProcessing.getAllPaymentOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Optional: Endpoint to update a payment order.
     * (Less common for payment processing once submitted, but could be for administrative updates).
     *
     * @param paymentId The unique identifier of the payment to update.
     * @param updatedPaymentOrder The updated payment order details.
     * @return A ResponseEntity indicating success or failure.
     */
    @PutMapping("/orders/{paymentId}")
    public ResponseEntity<String> updatePaymentOrder(@PathVariable String paymentId, @RequestBody PaymentOrder updatedPaymentOrder) {
        boolean success = paymentProcessing.updatePayment(paymentId, updatedPaymentOrder);
        if (success) {
            return new ResponseEntity<>("Payment order " + paymentId + " updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update payment order " + paymentId + " or not found.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Optional: Endpoint to cancel/delete a payment order.
     * (Cancellation is more common than true "deletion" in financial systems).
     *
     * @param paymentId The unique identifier of the payment to cancel.
     * @return A ResponseEntity indicating success or failure.
     */
    @DeleteMapping("/orders/{paymentId}")
    public ResponseEntity<String> cancelPaymentOrder(@PathVariable String paymentId) {
        boolean success = paymentProcessing.cancelPayment(paymentId);
        if (success) {
            return new ResponseEntity<>("Payment order " + paymentId + " cancelled successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to cancel payment order " + paymentId + " or not found/not cancellable.", HttpStatus.BAD_REQUEST);
        }
    }
}