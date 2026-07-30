package com.ifmd.mercaditolibre.dto;

public class AuthResponse {

    private String token;
    private String username;
    private String nombre;
    private String role;

    // Constructor vacío
    public AuthResponse() {
    }

    // Constructor solo con token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Constructor con todos los parámetros
    public AuthResponse(String token, String username, String nombre, String role) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.role = role;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}