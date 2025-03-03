package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
//import com.wora.gotYou.dtos.student.CreateStudentDto;
//import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.entities.Student;
import com.wora.gotYou.entities.Student;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.exceptions.EntityNotFoundException;
import com.wora.gotYou.mappers.StudentMapper;
import com.wora.gotYou.repositories.StudentRepository;
import com.wora.gotYou.repositories.StudentRepository;
import com.wora.gotYou.repositories.UserRepository;
import com.wora.gotYou.services.interfaces.StudentServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl  implements StudentServiceInter {

    private final StudentRepository studentRepository;
    private final StudentMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public StudentDto save(CreateStudentDto dto) {
        Student student = userMapper.toEntity(dto);
        Student savedStudent = studentRepository.save(student);
        return userMapper.toDTO(savedStudent);
    }

    @Override
    public StudentDto update(UpdateStudentDto dto, Long id) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
//        userMapper.updateStudentFromDto(dto, existingStudent);
        Student updatedStudent = studentRepository.save(existingStudent);
        return userMapper.toDTO(updatedStudent);
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    public User getStudentById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public StudentDto findByStudentName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Student user = studentRepository.findByUserName(username);
        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}