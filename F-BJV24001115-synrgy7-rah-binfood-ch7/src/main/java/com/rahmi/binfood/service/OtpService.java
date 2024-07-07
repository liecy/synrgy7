package com.rahmi.binfood.service;

public interface OtpService {
    void generateOTP(String email);
    boolean validateOTP(String email, String otp);
}