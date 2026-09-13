package com.canureal.cartflame.dtos;

public class UserCrudDto {
   public record RegisterRequestDto(
        String email,
        String username,
        String password
   ){}

   public record LoginRequestDto(
         String email,
         String password
   ){}

   public record LoginResponseDto(
         String email,
         String role
   ) {}
}
