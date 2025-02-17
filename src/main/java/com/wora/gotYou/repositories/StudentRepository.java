package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
