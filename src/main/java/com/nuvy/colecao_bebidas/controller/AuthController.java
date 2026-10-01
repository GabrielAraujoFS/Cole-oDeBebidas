package com.nuvy.colecao_bebidas.controller;

import com.nuvy.colecao_bebidas.dto.LoginRequestDTO;
import com.nuvy.colecao_bebidas.dto.LoginResponseDTO;
import com.nuvy.colecao_bebidas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${admin.usuario}")
    private String adminUsuario;

    @Value("${admin.senha-hash}")
    private String adminSenhaHash;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        if (!dto.usuario().equals(adminUsuario) || !passwordEncoder.matches(dto.senha(), adminSenhaHash)) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = jwtUtil.gerarToken(dto.usuario());
        return new LoginResponseDTO(token);
    }
}