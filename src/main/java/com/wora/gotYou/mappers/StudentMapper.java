package com.wora.gotYou.mappers;
import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.entities.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDTO(Student entity);
    Student toEntity(StudentDto dto);
    Student toEntity(CreateStudentDto dto);
    Student toEntity(UpdateStudentDto updateDto);
}
