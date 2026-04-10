package com.itheima.logmanagementsystem.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{userId}")
@Component
public class WebSocketServer {

    // 存放所有在线客户端的会话
    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessionMap.put(userId, session);
        System.out.println("WebSocket 客户端连接成功，UserId: " + userId);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        sessionMap.remove(userId);
        System.out.println("WebSocket 客户端断开连接，UserId: " + userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("WebSocket 发生错误");
        error.printStackTrace();
    }

    /**
     * 发送推送消息给指定用户
     */
    public static void sendMessageToUser(String userId, String message) {
        Session session = sessionMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
