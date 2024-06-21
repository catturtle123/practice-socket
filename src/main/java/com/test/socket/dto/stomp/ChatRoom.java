package com.test.socket.dto.stomp;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatRoom {
    private String roomId; // 방번호
    private String name; // 방제목

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        return chatRoom;
    }
}
