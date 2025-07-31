package com.ejemplo.controller;

import com.ejemplo.dto.LoginRequest;
import com.ejemplo.dto.LoginResponse;
import com.ejemplo.dto.UsuarioCreateDto;
import com.ejemplo.dto.UsuarioDto;
import com.ejemplo.security.JwtService;
import com.ejemplo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UsuarioDto register(@RequestBody @Valid UsuarioCreateDto dto) {
        return usuarioService.create(dto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String role = auth.getAuthorities().iterator().next().getAuthority().substring(5);
        String access = jwtService.generateAccessToken(request.email(), Map.of("role", role));
        String refresh = jwtService.generateRefreshToken(request.email());
        return new LoginResponse(access, refresh);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestParam String refreshToken) {
        String subject = jwtService.extractSubject(refreshToken);
        var user = usuarioService.findByEmail(subject);
        String access = jwtService.generateAccessToken(subject, Map.of("role", user.getRole()));
        String newRefresh = jwtService.generateRefreshToken(subject);
        return new LoginResponse(access, newRefresh);
    }
}
