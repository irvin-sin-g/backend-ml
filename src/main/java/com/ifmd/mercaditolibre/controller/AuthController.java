package com.ifmd.mercaditolibre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmd.mercaditolibre.dto.AuthRequest;
import com.ifmd.mercaditolibre.dto.AuthResponse;
import com.ifmd.mercaditolibre.dto.RegistroRequest;
import com.ifmd.mercaditolibre.model.UsuarioEntity;
import com.ifmd.mercaditolibre.services.UsuarioService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import com.ifmd.mercaditolibre.security.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider, UsuarioService usuarioService){
            this.authenticationManager = authenticationManager;
            this.jwtTokenProvider = jwtTokenProvider;
            this.usuarioService = usuarioService;
        }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken
            (request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        User userPrincipal = (User) authentication.getPrincipal();
        String authority = userPrincipal.getAuthorities().stream()
        .findFirst()
        .map(auth -> auth.getAuthority())
        .orElse("ROLE_CLIENTE");

        return ResponseEntity.ok(
            new AuthResponse(token, usuarioEntity.getUsername(),
        usuarioEntity.getNombre(), authority)
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequest request){
        try{
            UsuarioEntity usuario = usuarioService.saveUsuario(request);
            return ResponseEntity.ok(usuario);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
     
    }

}
