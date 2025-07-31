package com.ejemplo.repository;

import com.ejemplo.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @EntityGraph(attributePaths = "pedidos")
    Optional<Usuario> findWithPedidosById(Long id);
}
