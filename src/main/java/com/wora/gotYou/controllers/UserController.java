package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInter userService;

    @PostMapping("/upload-profile-image")
    public ResponseEntity<UserDto> uploadProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId
    ) throws IOException {
        UserDto updatedUser = userService.uploadProfileImage(Long.valueOf(userId), file);
        return ResponseEntity.ok(updatedUser);
    }
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
        System.out.println(dto.getBirthDate());
        UserDto updatedUser = userService.update(dto, id);
        System.out.println("Données mises à jour : " + updatedUser.toString());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto user = userService.findByUserName();
        System.out.println("user image from UseController : " + user.getProfileImage());

        return ResponseEntity.ok(user);
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        UserDto user = userService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}