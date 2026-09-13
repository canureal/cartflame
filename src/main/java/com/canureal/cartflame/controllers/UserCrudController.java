package com.canureal.cartflame.controllers;

import com.canureal.cartflame.dtos.UserCrudDto;
import com.canureal.cartflame.models.Users;
import com.canureal.cartflame.services.JwtService;
import com.canureal.cartflame.services.UserCrudService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserCrudController {
    private final UserCrudService userCrudService;

    @DeleteMapping("/delete")
    public Users deleteUser(@RequestBody UserCrudDto.DeleteRequestDto dto) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userCrudService.deleteUser(userId, dto.password());
    }
}
