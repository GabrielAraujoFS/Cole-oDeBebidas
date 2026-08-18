package com.nuvy.colecao_bebidas;

public class GeradorDeSenha {
    public static void main(String[] args) {
        String senha = "pitu0101";
        String hash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(senha);
        System.out.println(hash);
    }
}
