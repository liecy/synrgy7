package com.rahmi.binfood.chat_service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.stereotype.Component;

@Component
public class SocketIOController {
    private final SocketIOServer socketIOServer;

    public SocketIOController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;

        ConnectListener onUserConnected = socketIOClient ->
                System.out.println("SocketIOController onConnected");
        this.socketIOServer.addConnectListener(onUserConnected);
        DisconnectListener onUserDisconnected = socketIOClient ->
                System.out.println("SocketIOController disconnected");
        this.socketIOServer.addDisconnectListener(onUserDisconnected);
        DataListener<ChatMessage> onMessageSent = new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChatMessage message, AckRequest ackRequest) throws Exception {
                socketIOServer.getBroadcastOperations().sendEvent(message.getTo(), message);
                ackRequest.sendAckData("Message sent to " + message.getTo());
            }
        };
        this.socketIOServer.addEventListener("chat-room", ChatMessage.class, onMessageSent);
    }

}
