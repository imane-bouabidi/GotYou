package com.wora.gotYou.repositories;

import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.entities.Student;
import com.wora.gotYou.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserName(String username);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByCin(String cin);
}
