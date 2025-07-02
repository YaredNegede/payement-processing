package com.kifiya.payment.processing.out.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.payment.processing.core.entities.Payment;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.processing.in.provider.CBEProvider;
import com.kifiya.payment.processing.mapper.PaymentMapper;
import com.kifiya.payment.processing.out.provider.dtos.CBEResponse;
import com.kifiya.payment.processing.out.storage.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class CBEGateWayProvider implements GateWayProvider {

    @Value("${cbe.baseurl}")
    private String url;

    private static final String TOPIC_SUCCUSS = "cbe-success";

    private static final String TOPIC_FAILLED = "cbe-failled";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    private final PaymentMapper paymentMapper;

    private final PaymentRepository paymentRepository;

    public CBEGateWayProvider(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper mapper,
                              RestTemplate restTemplate, PaymentMapper paymentMapper,
                              PaymentRepository paymentRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(topics = CBEProvider.TOPIC_INGEST, groupId = "cbe-group")
    @Override
    public String ingestPayment(String paymentOrder) throws JsonProcessingException {
        PaymentOrder order = mapper.readValue(paymentOrder, PaymentOrder.class);
        Payment payment = paymentMapper.toEntity(order.getPaymentRequestDto());
        ResponseEntity<CBEResponse> response = restTemplate.postForEntity(String.format("%s/process",url), order.getPaymentRequestDto(), CBEResponse.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            paymentRepository.save(payment);
            kafkaTemplate.send(TOPIC_SUCCUSS,paymentOrder);
        } else {
            kafkaTemplate.send(TOPIC_FAILLED,paymentOrder);
        }
        return response.getBody().getId();
    }

    @KafkaListener(topics = CBEProvider.TOPIC_UPDATE, groupId = "cbe-group")
    @Override
    public boolean updatePayment(String updatedPaymentOrder) throws JsonProcessingException {
        PaymentOrder order = mapper.readValue(updatedPaymentOrder, PaymentOrder.class);
        Payment payment = paymentMapper.toEntity(order.getPaymentRequestDto());
        boolean existingPayment = paymentRepository.existsById(payment.getId());
        if(existingPayment){
            ResponseEntity<CBEResponse> response = restTemplate.postForEntity(String.format("%s/update",url), order.getPaymentRequestDto(), CBEResponse.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                paymentRepository.save(payment);
                kafkaTemplate.send(TOPIC_SUCCUSS,updatedPaymentOrder);
                return true;
            } else {
                kafkaTemplate.send(TOPIC_FAILLED,updatedPaymentOrder);
            }
        }
        return false;
    }
}
