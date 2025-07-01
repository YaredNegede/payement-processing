package com.kifiya.payment.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public abstract class Response <E,B>{
    List<E> errors;
    B body;
}
