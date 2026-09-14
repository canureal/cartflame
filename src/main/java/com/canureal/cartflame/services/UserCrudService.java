package com.canureal.cartflame.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.canureal.cartflame.dtos.UserCrudDto;
import com.canureal.cartflame.models.Users;
import com.canureal.cartflame.models.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCrudService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserCrudDto.RegisterRequestDto dto) {
        Users user = new Users();
        String email = dto.email();
        String username = dto.username();
        String hashed_password = passwordEncoder.encode(dto.password());
        user.setPassword(hashed_password);
        user.setEmail(email);
        user.setUsername(username);
        usersRepository.save(user);
    }

    public Users loginUser(UserCrudDto.LoginRequestDto dto) {
        Users user =  usersRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid email"));

        if (dto.password().isEmpty() || dto.email().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or password cannot be empty");
        }

        if(!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid password");
        }

        return user;
    }

    // we will get the id from security context
    public Users deleteUser(UUID userId, String password) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "user with id " + userId + " not found"
                ));
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid password");
        }

        usersRepository.deleteById(userId);
        return user;
    }
}
