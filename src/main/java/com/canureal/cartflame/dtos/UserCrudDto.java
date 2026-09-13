package com.canureal.cartflame.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCrudDto {
   // before, i forgot to add validation
   // i completed the least product i can do rn
   // so it's time to add those validations
   // 13.09.2026 - 14:34 UTC+3
   public record RegisterRequestDto(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank @Size(min = 8) String password
   ){}

   public record LoginRequestDto(
         @NotBlank String email,
         @NotBlank String password
   ){}

   public record DeleteRequestDto(
           @NotBlank String password
   ) {}

   public record LoginResponseDto(
         String email,
         String role
   ) {}
}
