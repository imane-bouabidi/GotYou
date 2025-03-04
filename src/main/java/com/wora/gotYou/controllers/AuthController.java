package com.wora.gotYou.controllers;


import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.services.implementation.StudentServiceImpl;
import com.wora.gotYou.services.interfaces.StudentServiceInter;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceInter userService;
    private final StudentServiceInter studentService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto dto) {
        UserDto savedUser = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PostMapping("/register/student")
    public ResponseEntity<StudentDto> registerStudent(@RequestBody CreateStudentDto dto) {
        StudentDto savedUser = studentService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return ResponseEntity.ok("Bearer " + token);
    }
}