package com.kifiya.payment.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class RestTemplateWithRetryConfig {

    @Value("${com.kifiya.payment.maxretry}")
    private int maxretry;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(throwable -> {
            if (throwable instanceof HttpServerErrorException) {
                HttpStatusCode statusCode = ((HttpServerErrorException) throwable).getStatusCode();
                if (statusCode.is5xxServerError()) {
                    return new SimpleRetryPolicy(maxretry, Collections.singletonMap(HttpServerErrorException.class, true));
                }
            } else if (throwable instanceof HttpClientErrorException.TooManyRequests) {
                return new SimpleRetryPolicy(maxretry, Collections.singletonMap(HttpClientErrorException.TooManyRequests.class, true));
            } else if (throwable instanceof ResourceAccessException) {
                return new SimpleRetryPolicy(maxretry, Collections.singletonMap(ResourceAccessException.class, true));
            }
            return new SimpleRetryPolicy(maxretry, Collections.emptyMap());
        });

        retryTemplate.setRetryPolicy(policy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500L);
        backOffPolicy.setMaxInterval(5000L);
        backOffPolicy.setMultiplier(2.0);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    @Bean
    public ClientHttpRequestInterceptor retryInterceptor(RetryTemplate retryTemplate) {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

                try {
                    return retryTemplate.execute(new RetryCallback<ClientHttpResponse, IOException>() {
                        @Override
                        public ClientHttpResponse doWithRetry(RetryContext context) throws IOException {
                            if (context.getRetryCount() > 0) {
                                System.out.println("Retrying request to " + request.getURI() + ". Attempt: " + (context.getRetryCount() + 1));
                            }
                            return execution.execute(request, body);
                        }
                    });
                } catch (Throwable e) {
                    System.err.println("Request to " + request.getURI() + " failed after retries: " + e.getMessage());
                    if (e instanceof IOException) {
                        throw (IOException) e;
                    } else if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    }
                    throw new RuntimeException("Unexpected error during retryable HTTP call", e);
                }
            }
        };
    }

}