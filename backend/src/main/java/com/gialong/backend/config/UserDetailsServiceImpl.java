package com.gialong.backend.config;


import com.gialong.backend.dto.UserDTO;
import com.gialong.backend.service.RoleService;
import com.gialong.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findByEmail(username);
        return User
                .withUsername(username)
                .password(userDTO.getPassword())
                .disabled(!userDTO.isActive())
                .roles(roleService.findById(userDTO.getRoleId()).getName().name())
                .build();
    }
}