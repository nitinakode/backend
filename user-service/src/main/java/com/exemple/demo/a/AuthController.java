package com.exemple.demo.a;


import com.exemple.demo.exception.CredentialsDto;
import com.exemple.demo.exception.User;
import com.exemple.demo.exception.UserDto;
import com.exemple.demo.exception.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/")
public class AuthController {

    private final UserService authService;

    @PostMapping("login")

    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        return ResponseEntity.ok(authService.login(credentialsDto));
    }
    @PostMapping("saveDetailsOfUsers") // pending
    public ResponseEntity<UserDto> saveDetailsOfUsers(@RequestBody UserDto user) {
        return ResponseEntity.ok(authService.callSaveDetailsOfUsers(user));
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> saveDetailsOfUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }


}