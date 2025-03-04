package com.wora.gotYou.mappers;
import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDTO(Student entity);
    Student toEntity(StudentDto dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inscriptionDate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    Student toEntity(CreateStudentDto dto);
    Student toEntity(UpdateStudentDto updateDto);
}
