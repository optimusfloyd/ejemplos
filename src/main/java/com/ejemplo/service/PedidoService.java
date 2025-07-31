package com.ejemplo.service;

import com.ejemplo.dto.PedidoCreateDto;
import com.ejemplo.dto.PedidoDto;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Usuario;
import com.ejemplo.repository.PedidoRepository;
import com.ejemplo.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PedidoDto create(Long userId, PedidoCreateDto dto) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow();
        Pedido pedido = Pedido.builder()
                .descripcion(dto.descripcion())
                .usuario(usuario)
                .build();
        pedidoRepository.save(pedido);
        return new PedidoDto(pedido.getId(), pedido.getDescripcion());
    }

    public Page<PedidoDto> list(Pageable pageable) {
        return pedidoRepository.findAll(pageable)
                .map(p -> new PedidoDto(p.getId(), p.getDescripcion()));
    }
}
