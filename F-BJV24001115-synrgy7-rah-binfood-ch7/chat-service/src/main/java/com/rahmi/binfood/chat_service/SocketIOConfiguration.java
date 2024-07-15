package com.rahmi.binfood.chat_service;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class SocketIOConfiguration {
    private SocketIOServer socketIOServer;

    @Value("${socket-io.hostname}")
    private String hostname;

    @Value("${socket-io.port}")
    private int port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration configuration = new Configuration();
        configuration.setHostname(hostname);
        configuration.setPort(port);
        socketIOServer = new SocketIOServer(configuration);
        socketIOServer.start();

        socketIOServer.addConnectListener(socketIOClient -> {
            System.out.println("A new client is connected to " + socketIOClient.getSessionId());
        });

        socketIOServer.addDisconnectListener(socketIOClient ->
                System.out.println("A client is disconnected from " + socketIOClient.getSessionId()));

        return socketIOServer;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        socketIOServer.stop();
    }
}

