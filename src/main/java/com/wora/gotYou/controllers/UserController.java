package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInter userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto dto) {
        UserDto savedUser = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserDto dto
    ) {
        UserDto updatedUser = userService.update(dto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}