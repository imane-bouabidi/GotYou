package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.mappers.UserMapper;
import com.wora.gotYou.repositories.UserRepository;
import com.wora.gotYou.security.JwtTokenProvider;
import com.wora.gotYou.services.interfaces.UserServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInter {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDto save(CreateUserDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(UserStatus.PENDING);
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
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

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDTO(user);
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

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtTokenProvider.generateToken(username);
    }
}
