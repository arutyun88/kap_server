package com.kap.kap_server.service;

public interface SmsService {
    void sendAuthorizationCode(String phoneNumber);
}
