package com.canureal.cartflame.controllers;

import com.canureal.cartflame.dtos.UserCrudDto;
import com.canureal.cartflame.models.Users;
import com.canureal.cartflame.services.JwtService;
import com.canureal.cartflame.services.UserCrudService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

@RestController @RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserCrudController {
    private final UserCrudService userCrudService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    UserCrudDto.RegisterRequestDto signup(@RequestBody UserCrudDto.RegisterRequestDto dto) {
        userCrudService.registerUser(dto);
        return dto;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserCrudDto.LoginResponseDto> signin(@RequestBody UserCrudDto.LoginRequestDto dto, HttpServletResponse response) {
        Users user = userCrudService.loginUser(dto);
        String token = jwtService.generateToken(user.getId(), user.getRole());

        Cookie cookie = new Cookie("session_token", token);
        cookie.setHttpOnly(true);
        // ATTENTION TURN cookie.setSecure() true in PRODUCTION IF YOU EVER GO
        cookie.setSecure(false);
        // ATTENTION !!!!!!!
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(new UserCrudDto.LoginResponseDto(user.getUsername(), user.getRole()));
    }
}
