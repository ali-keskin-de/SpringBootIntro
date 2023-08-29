package com.tpe.security.service;



import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //!! bu class'ta override ettigim loadUserByUsername method'u  ile ben kendi olusturdugum User class'i verecegim
    // bu method  Spring security'nin anlayacagi UserDetail'e  type cevirecek.
    // VE bu yeterli degil Role'rimide GrantedAuthority dönüstürmem gerekir


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user= userRepository.findByUserName(username).orElseThrow(()->
                     new ResourceNotFoundException("User not found  with username : " + username));

       if (user != null){
           return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                     buildGrantedAuthorities(user.getRoles()));

       }else{
           throw new UsernameNotFoundException("user not found username : " + username);
       }

    }

    // Bu method ile rollerimizi GrantedAuthority cevriyor
    // bunun icinde bu interface implement eden concreat bir class olan SimpleGrantedAuthority yardimiyla yaptim.
    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<Role> roles){

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role: roles
             ) {

            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;

    }

}


