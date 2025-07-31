package com.ejemplo.controller;

import com.ejemplo.dto.PedidoCreateDto;
import com.ejemplo.dto.PedidoDto;
import com.ejemplo.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PedidoDto create(@PathVariable Long userId, @RequestBody @Valid PedidoCreateDto dto) {
        return pedidoService.create(userId, dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<PedidoDto> list(Pageable pageable) {
        return pedidoService.list(pageable);
    }
}
