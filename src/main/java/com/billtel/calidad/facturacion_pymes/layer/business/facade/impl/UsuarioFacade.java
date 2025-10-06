package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IUsuarioService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioFacade implements IUsuarioFacade {

    private final IUsuarioService usuarioService;
    private final IEmpresaService empresaService;
    private final UsuarioDtoMapper usuarioDtoMapper;
    private final UsuarioRequestMapper usuarioRequestMapper;

    @Override
    public Optional<UsuarioDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UsuarioDto> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Iterable<UsuarioDto> findAll() {
        return null;
    }

    @Override
    public UsuarioDto create(UsuarioRequest request) {
        return null;
    }

    @Override
    public Optional<UsuarioDto> update(UsuarioRequest request, Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
