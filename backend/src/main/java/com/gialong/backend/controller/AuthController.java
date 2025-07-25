package com.gialong.backend.controller;


import com.gialong.backend.dto.CredentilsDTO;
import com.gialong.backend.dto.UserDTO;
import com.gialong.backend.exception.AppException;
import com.gialong.backend.service.JwtService;
import com.gialong.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody CredentilsDTO credentilsDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentilsDTO.email(), credentilsDTO.password())
            );

            User user = (User) authentication.getPrincipal();
            String role = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER");

            String jwtToken = jwtService.generateToken(credentilsDTO.email(), role);
            Map<String, String> map = new HashMap<>();
            map.put("token", jwtToken);
            return ResponseEntity.ok(map);

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.FORBIDDEN, "Incorrect email or password", e);
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.FORBIDDEN, "User is disable", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<UUID> create(@RequestBody UserDTO userDTO) {
        if(!userDTO.getPassword().isBlank()) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
//        userDTO.setRoleId(1); // 1 is USER
        try {
            UUID id = userService.create(userDTO);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
            System.out.println(constraintName);
            if(constraintName.equals("user.UK_ob8kqyqqgmefl0aco34akdtpe")) {
                throw new AppException(HttpStatus.BAD_REQUEST, "Email already exist", e);
            }
            throw new RuntimeException(e);
        }
    }

}