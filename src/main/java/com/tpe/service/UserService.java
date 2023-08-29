package com.tpe.service;


import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

//Note: register()********************************************************
    public void saveUser(UserRequest userRequest) {

        User myUser = new User();
        myUser.setName(userRequest.getFirstName());
        myUser.setLastName(userRequest.getLastName());
        myUser.setUserName(userRequest.getUserName());
        //myUser.setPassword(userRequest.getPassword());
        String password= userRequest.getPassword();
        String encoded = passwordEncoder.encode(password);
        myUser.setPassword(encoded);
       Role role= roleService.getRoleByType(UserRole.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        myUser.setRoles(roles);

        userRepository.save(myUser);

    }
}
