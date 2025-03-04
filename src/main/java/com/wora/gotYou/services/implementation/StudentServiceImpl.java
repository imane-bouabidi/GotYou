package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
//import com.wora.gotYou.dtos.student.CreateStudentDto;
//import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.entities.Student;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.exceptions.DuplicateFieldNameException;
import com.wora.gotYou.exceptions.EntityNotFoundException;
import com.wora.gotYou.mappers.StudentMapper;
import com.wora.gotYou.repositories.StudentRepository;
import com.wora.gotYou.services.interfaces.StudentServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl  implements StudentServiceInter {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDto save(CreateStudentDto dto) {
        if (studentRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new DuplicateFieldNameException("Username already exists: " + dto.getUserName());
        } else if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateFieldNameException("Email already exists: " + dto.getEmail());
        } else if (studentRepository.findByCin(dto.getCin()).isPresent()) {
            throw new DuplicateFieldNameException("Student account with this CIN already exists: " + dto.getCin());
        }
        Student student = studentMapper.toEntity(dto);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setRole(Role.STUDENT);
        student.setStatus(UserStatus.PENDING);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentDto update(UpdateStudentDto dto, Long id) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
//        studentMapper.updateStudentFromDto(dto, existingStudent);
        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public StudentDto findByStudentName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Student user = studentRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return studentMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}