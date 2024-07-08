package com.rahmi.binfood.service;

import com.rahmi.binfood.service.EmailService;
import com.rahmi.binfood.service.OtpService;
import com.rahmi.binfood.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final Map<String, String> otpCache = new HashMap<>();
    private final EmailService emailService;

    @Autowired
    public OtpServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void generateOTP(String email) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        otpCache.put(email, otp);
        emailService.sendEmail(email, "Your OTP Code", "Your OTP code is: " + otp);
    }

    @Override
    public boolean validateOTP(String email, String otp) {
        return otpCache.containsKey(email) && otpCache.get(email).equals(otp);
    }
}
