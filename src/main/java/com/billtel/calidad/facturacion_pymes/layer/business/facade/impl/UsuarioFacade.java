package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioCreateRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IUsuarioService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UsuarioFacade implements IUsuarioFacade {

    private final IUsuarioService usuarioService;
    private final UsuarioDtoMapper usuarioDtoMapper;
    private final UsuarioRequestMapper usuarioRequestMapper;
    private final UsuarioCreateRequestMapper usuarioCreateRequestMapper;

    @Override
    public Optional<UsuarioDto> findById(Long id) {
        var user = usuarioService.findById(id);

        return user.map(usuarioDtoMapper::toDto);
    }

    @Override
    public Optional<UsuarioDto> findByUsername(String username) {
        var user = usuarioService.findByUsername(username);

        return user.map(usuarioDtoMapper::toDto);
    }

    @Override
    public List<UsuarioDto> findAll() {
        var users = usuarioService.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(usuarioDtoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public UsuarioDto create(UsuarioCreateRequest request) {
        var userRequest = usuarioCreateRequestMapper.toDomain(request);
        var userCreated = usuarioService.save(userRequest);
        return usuarioDtoMapper.toDto(userCreated);
    }

    @Override
    public Optional<UsuarioDto> update(UsuarioRequest request, Long id) {
        var userToUpdate = usuarioRequestMapper.toDomain(request);

        var userUpdated = usuarioService.update(userToUpdate, id);

        return userUpdated.map(usuarioDtoMapper::toDto);
    }

    @Override
    public void delete(Long id) {

        var user = usuarioService.findById(id);

        //aca faltaría lógica para validar jeje

        usuarioService.delete(id);
    }
}
