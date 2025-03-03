package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

    TwoFactorOTP findByUserId(Long userId);
}
