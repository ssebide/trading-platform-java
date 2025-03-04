package com.ssebide.service;

import com.ssebide.domain.VerificationType;
import com.ssebide.modal.ForgotPasswordToken;
import com.ssebide.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
