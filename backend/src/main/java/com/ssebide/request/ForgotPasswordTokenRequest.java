package com.ssebide.request;

import com.ssebide.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

    private String sendTo;

    private VerificationType verificationType;
}
