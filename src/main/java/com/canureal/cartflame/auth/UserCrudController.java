package com.canureal.cartflame.auth;

import com.canureal.cartflame.dtos.UserCrudDto;
import com.canureal.cartflame.models.Users;
import com.canureal.cartflame.services.JwtService;
import com.canureal.cartflame.services.UserCrudService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    UserCrudDto.LoginRequestDto signin(@RequestBody UserCrudDto.LoginRequestDto dto, HttpServletResponse response) {
        Users user = userCrudService.loginUser(dto);
        String token = jwtService.generateToken(user.getId());

        Cookie cookie = new Cookie("session_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return dto;
    }
}
