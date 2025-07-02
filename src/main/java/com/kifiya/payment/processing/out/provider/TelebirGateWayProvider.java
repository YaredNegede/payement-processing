package com.kifiya.payment.processing.out.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.processing.in.provider.TelebirProvider;
import com.kifiya.payment.processing.mapper.PaymentMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class TelebirGateWayProvider implements GateWayProvider {


    private static final String TOPIC_SUCCUSS = "telebir-success";

    private static final String TOPIC_FAILLED = "telebir-failled";


    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    private final PaymentMapper paymentMapper;

    public TelebirGateWayProvider(KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper mapper,
                                  PaymentMapper paymentMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
        this.paymentMapper = paymentMapper;
    }

    @KafkaListener(topics = TelebirProvider.TOPIC_INGEST, groupId = "telebir-ingest-group")
    @Override
    public String ingestPayment(String paymentOrder) throws JsonProcessingException {
        return "";
    }

    @KafkaListener(topics = TelebirProvider.TOPIC_UPDATE, groupId = "telebir-update-group")
    @Override
    public boolean updatePayment(String updatedPaymentOrder) throws JsonProcessingException {
        return false;
    }
}
