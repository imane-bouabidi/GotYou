package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.exceptions.EntityNotFoundException;
import com.wora.gotYou.mappers.UserMapper;
import com.wora.gotYou.repositories.UserRepository;
import com.wora.gotYou.security.JwtTokenProvider;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInter {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${upload.directory}")
    private String uploadDirectory;

    public UserDto save(CreateUserDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(UserStatus.PENDING);
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDto uploadProfileImage(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        user.setProfileImage("/uploads/" + fileName);
        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
    }

    public UserDto update(UpdateUserDto dto,Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.toEntity(dto);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found with email: " + email));

        return userMapper.toDTO(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDto updateStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDto findByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//        System.out.println(username);
        User user = userRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        System.out.println("user image from UserServiceImpl : " + user.getProfileImage());
        return userMapper.toDTO(user);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        System.out.println(user.getRole());
        return jwtTokenProvider.generateToken(username);
    }

//    public UserDto getCurrentUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//    }
}
