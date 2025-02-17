package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDTO(User entity);
    User toEntity(UserDto dto);
    User toEntity(CreateUserDto dto);
    User toEntity(UpdateUserDto updateDto);
}
