package com.ejemplo.service;

import com.ejemplo.dto.UsuarioCreateDto;
import com.ejemplo.dto.UsuarioDto;
import com.ejemplo.model.Usuario;
import com.ejemplo.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioDto create(UsuarioCreateDto dto) {
        Usuario usuario = Usuario.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role("USER")
                .build();
        usuarioRepository.save(usuario);
        return new UsuarioDto(usuario.getId(), usuario.getEmail(), usuario.getRole());
    }

    public Page<UsuarioDto> list(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(u -> new UsuarioDto(u.getId(), u.getEmail(), u.getRole()));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
