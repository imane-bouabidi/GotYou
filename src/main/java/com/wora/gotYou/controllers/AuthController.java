package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.auth.LoginRequest;
import com.wora.gotYou.dtos.auth.LoginResponse;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.security.JwtTokenProvider;
import com.wora.gotYou.services.implementation.UserServiceImpl;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceInter userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto dto) {
        UserDto savedUser = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtTokenProvider.generateToken(authentication);
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok(new LoginResponse(token, role));
    }
}