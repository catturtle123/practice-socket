package com.test.socket.dto.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    public enum MessageType{
        ENTER, TALK, OUT
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
