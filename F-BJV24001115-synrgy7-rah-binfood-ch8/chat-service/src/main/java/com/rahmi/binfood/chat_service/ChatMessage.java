package com.rahmi.binfood.chat_service;

import lombok.Data;

@Data
public class ChatMessage {
    private String from;
    private String to;
    private String content;
}