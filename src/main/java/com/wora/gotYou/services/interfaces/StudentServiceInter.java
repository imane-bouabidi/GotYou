package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.student.CreateStudentDto;
import com.wora.gotYou.dtos.student.UpdateStudentDto;
import com.wora.gotYou.dtos.student.StudentDto;
import com.wora.gotYou.services.GenericService;

public interface StudentServiceInter extends GenericService<CreateStudentDto, UpdateStudentDto, StudentDto, Long> {

    StudentDto getStudentById(Long id);
}