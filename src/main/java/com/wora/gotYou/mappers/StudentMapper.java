package com.wora.gotYou.mappers;
import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.entities.Student;

public interface StudentMapper {
    UpdateStudentDto toDTO(Student entity);
    Student toEntity(CreateStudentDto dto);
    Student toEntity(UpdateStudentDto updateDto);
}
