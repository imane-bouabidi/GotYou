package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.enums.RequestStatus;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.services.implementation.RequestServiceImpl;
import com.wora.gotYou.services.interfaces.RequestServiceInter;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/admin")
//@RequiredArgsConstructor
public class AdminController {

    private final UserServiceInter userService;
    private final RequestServiceInter requestService;

    public AdminController(UserServiceInter userService, RequestServiceInter requestService) {
        this.userService = userService;
        this.requestService = requestService;
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

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/status/{id}")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable Long id, @RequestBody String status) {
        UserStatus userStatus = UserStatus.valueOf(status.replaceAll("\"", ""));
        UserDto updatedUser = userService.updateStatus(id, userStatus);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long id, @RequestBody String roleRequest) {
        UserDto updatedUser = userService.updateRole(id, Role.valueOf(roleRequest.replaceAll("\"", "")));
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/student-requests/status/{id}")
    public ResponseEntity<RequestDto> updateStudentRequestStatus(
            @PathVariable Long id,
            @RequestBody String statusRequest
    ) {
        RequestStatus requestStatus = RequestStatus.valueOf(statusRequest.replaceAll("\"", ""));

        RequestDto updatedRequest = requestService.updateStatus(id, requestStatus);
        return ResponseEntity.ok(updatedRequest);
    }
}
