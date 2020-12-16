package com.project.online_library.service;

import com.project.online_library.dto.LoginDto;
import com.project.online_library.dto.UserDTO;
import com.project.online_library.model.Users;
import com.project.online_library.repository.UserRepository;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Users login(LoginDto loginDto){

        Users user = userRepository.findByUsername(loginDto.getUsername());
        return user;
    }
}
