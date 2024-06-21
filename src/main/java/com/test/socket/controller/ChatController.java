package com.test.socket.controller;

import com.test.socket.config.PrincipalDetails;
import com.test.socket.dto.stomp.ChatMessage;
import com.test.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {
//        @AuthenticationPrincipal UserDetails userDetails,
//        System.out.println(pricipalDetails.getUsername());
        messagingTemplate.convertAndSend("/topic/chat/room/" + chatMessage.getRoomId(), chatMessage.getMessage());
    }

    @GetMapping("/messages")
    public void messages(@AuthenticationPrincipal User user) {
//        System.out.println("1");
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//        @AuthenticationPrincipal UserDetails userDetails,
//        System.out.println(user.getUsername());
    }

//    @PostMapping("/wow")
//    public void wow() {
//        //final String uid = jwt.getSubject();
//        //log.debug("[UID]"+uid);
//        System.out.println("1123");
////        messagingTemplate.convertAndSend("/topic/chat/room" + 1, "메시지 도착");
//    }
}
