package com.test.socket.controller;

import com.test.socket.entity.User;
import com.test.socket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public ResponseEntity<User> join(@RequestParam(name = "id") String id, @RequestParam(name = "pw") String pw) {
        User user = User.builder().email(id).password(passwordEncoder.encode(pw)).build();
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
