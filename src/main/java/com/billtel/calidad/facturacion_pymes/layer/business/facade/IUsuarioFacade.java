package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;

import java.util.List;
import java.util.Optional;

public interface IUsuarioFacade {
    Optional<UsuarioDto> findById(Long id);
    Optional<UsuarioDto> findByUsername(String username);
    List<UsuarioDto> findAll();
    UsuarioDto create(UsuarioCreateRequest request);
    Optional<UsuarioDto> update(UsuarioRequest request, Long id);
    void delete(Long id);
}
