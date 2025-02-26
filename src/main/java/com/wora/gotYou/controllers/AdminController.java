package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
//@RequiredArgsConstructor
public class AdminController {

    private final UserServiceInter userService;

    public AdminController(UserServiceInter userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto dto) {
        UserDto savedUser = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PostMapping("/validate-user/{id}")
    public ResponseEntity<UserDto> validateUser(@PathVariable Long id, @RequestParam UserStatus status) {
        UserDto updatedUser = userService.updateStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }

}
