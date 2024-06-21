package com.test.socket.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.socket.dto.socket.ChatDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReplyEchoHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    Map<String, List<WebSocketSession>> webSockets = new LinkedHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session + "클라이언트 접속");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatDto chatDto = objectMapper.readValue(message.getPayload(), ChatDto.class);
        System.out.println(chatDto.getType());
        System.out.println(webSockets);

        if (chatDto.getType().equals(ChatDto.MessageType.ENTER)) {
            List<WebSocketSession> webSocketSessionArrayList = new ArrayList<>();
            if (!webSockets.containsKey(chatDto.getRoomId())) {
                webSocketSessionArrayList.add(session);
                webSockets.put(chatDto.getRoomId(), webSocketSessionArrayList);
            } else {
                if (!webSockets.get(chatDto.getRoomId()).contains(session)) {
                    List<WebSocketSession> webSocketSessions = webSockets.get(chatDto.getRoomId());
                    webSocketSessions.add(session);
                    webSockets.put(chatDto.getRoomId(), webSocketSessions);
                } else {
                    System.out.println("이미 존재하는 세션입니다.");
                }
            }
            System.out.println(session + "이 " + chatDto.getRoomId() + "에 접속 하였습니다.");
        } else if (chatDto.getType().equals(ChatDto.MessageType.TALK)) {
            if (webSockets.containsKey(chatDto.getRoomId())) {
                List<WebSocketSession> webSocketSessionArrayList;
                webSocketSessionArrayList = webSockets.get(chatDto.getRoomId());
                if (!webSocketSessionArrayList.contains(session)) {
                    System.out.println("ENTER을 먼저 해야합니다.");
                } else {
                    for (WebSocketSession s : webSockets.get(chatDto.getRoomId())) {
                        if (!s.equals(session)) {
                            s.sendMessage(message);
                        }
                    }
                }
            } else {
                System.out.println("세션에 접속한 것이 없습니다.");
            }
        } else if(chatDto.getType().equals(ChatDto.MessageType.OUT)) {
            if (webSockets.containsKey(chatDto.getRoomId())) {
                webSockets.get(chatDto.getRoomId()).remove(session);
                System.out.println(session + ": 아웃됨.");
            }
        }
    }



    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session + "클라이언트 아웃");
    }
}
