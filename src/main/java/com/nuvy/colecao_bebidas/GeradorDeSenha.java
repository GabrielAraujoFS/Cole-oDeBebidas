package com.nuvy.colecao_bebidas;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {
    public static void main(String[] args) {
        String senha = "Grafica2975"; // troque por uma senha de verdade, só sua
        String hash = new BCryptPasswordEncoder().encode(senha);
        System.out.println("HASH GERADO: " + hash);
    }
}