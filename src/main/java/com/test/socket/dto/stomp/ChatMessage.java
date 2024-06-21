package com.test.socket.dto.stomp;

import lombok.Data;

@Data
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, EXIT, MATCH, MATCH_REQUEST
    }

    // message 타입
    private MessageType type;
    // 방번호
    private String roomId;
    // 송신자
    private String sender;
    // 메시지 내용
    private String message;
}
