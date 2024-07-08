package com.banco.cuenta.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
 private String code;
 private String message;
}
