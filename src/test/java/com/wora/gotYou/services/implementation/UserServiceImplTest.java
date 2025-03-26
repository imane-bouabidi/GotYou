package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.mappers.UserMapper;
import com.wora.gotYou.repositories.UserRepository;
import com.wora.gotYou.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    private final String uploadDirectory = "uploads";

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, jwtTokenProvider);
        java.lang.reflect.Field field;
        try {
            field = UserServiceImpl.class.getDeclaredField("uploadDirectory");
            field.setAccessible(true);
            field.set(userService, uploadDirectory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set uploadDirectory", e);
        }
    }

    @Test
    void save_ShouldCreateUserSuccessfully() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setUserName("testuser");
        dto.setPassword("password123");
        dto.setEmail("test@example.com");

        User user = new User();
        user.setUserName("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("test@example.com");
        user.setStatus(UserStatus.PENDING);
        user.setRole(Role.STUDENT);

        UserDto userDto = new UserDto();
        userDto.setUserName("testuser");
        userDto.setEmail("test@example.com");

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDto);

        UserDto result = userService.save(dto);

        assertNotNull(result);
        assertEquals("testuser", result.getUserName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode("password123");
        assertEquals(UserStatus.PENDING, user.getStatus());
        assertEquals(Role.STUDENT, user.getRole());
    }


    @Test
    void uploadProfileImage_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.uploadProfileImage(userId, file));
        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void uploadProfileImage_ShouldThrowIOException_WhenFileCopyFails() throws IOException {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("profile.jpg");
        when(file.getInputStream()).thenThrow(new IOException("File copy failed"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(IOException.class, () -> userService.uploadProfileImage(userId, file));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void update_ShouldUpdateUserSuccessfully() {
        Long userId = 1L;
        UpdateUserDto dto = new UpdateUserDto();
        dto.setName("NewName");
        dto.setLastName("NewLastName");
        dto.setEmail("newemail@example.com");
        dto.setBirthDate("1990-01-01");

        User user = new User();
        user.setId(userId);
        user.setUserName("testuser");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUserName("testuser");
        updatedUser.setName("NewName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setBirthDate(LocalDate.of(1990, 1, 1));

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUserName("testuser");
        userDto.setName("NewName");
        userDto.setLastName("NewLastName");
        userDto.setEmail("newemail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(userDto);

        UserDto result = userService.update(dto, userId);

        assertNotNull(result);
        assertEquals("NewName", result.getName());
        assertEquals("NewLastName", result.getLastName());
        assertEquals("newemail@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        UpdateUserDto dto = new UpdateUserDto();
        dto.setName("NewName");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.update(dto, userId));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void update_ShouldHandleNullBirthDate() {
        Long userId = 1L;
        UpdateUserDto dto = new UpdateUserDto();
        dto.setName("NewName");
        dto.setLastName("NewLastName");
        dto.setEmail("newemail@example.com");
        dto.setBirthDate(null);

        User user = new User();
        user.setId(userId);
        user.setUserName("testuser");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUserName("testuser");
        updatedUser.setName("NewName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setEmail("newemail@example.com");

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUserName("testuser");
        userDto.setName("NewName");
        userDto.setLastName("NewLastName");
        userDto.setEmail("newemail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(userDto);

        UserDto result = userService.update(dto, userId);

        assertNotNull(result);
        assertEquals("NewName", result.getName());
        assertEquals("NewLastName", result.getLastName());
        assertEquals("newemail@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }
}