package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.dtos.user.UpdateUserDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.services.GenericService;

public interface UserServiceInter extends GenericService<CreateUserDto, UpdateUserDto, UserDto, Long> {
    User getUserById(Long id);
//    UserDto findByEmail(String email);
    UserDto updateStatus(Long id, UserStatus status);
    String login(String username, String password);
    UserDto findByUserName();
}
