package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Data
@Transactional
public class EmpresaServiceImpl implements IEmpresaService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Empresa> findAll() {
        return StreamSupport.stream(empresaRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Empresa> findByUsername(String username) {
        return empresaRepository.findByUsuarioUsername(username);
    }

    @Override
    public List<Empresa> findByUsuarioId(Long usuarioId) {
        return empresaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Optional<Empresa> findByIdAndUsuarioId(Long id, Long usuarioId) {
        return empresaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    @Override
    public Empresa save(String username, Empresa empresa) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        empresa.setUsuario(usuario);
        return empresaRepository.save(empresa);
    }

    @Override
    public Optional<Empresa> update(Empresa empresa, Long id) {
        return findById(id).map(empresaDB -> {
            empresaDB.setRuc(empresa.getRuc());
            empresaDB.setDireccion(empresa.getDireccion());
            empresaDB.setRazonSocial(empresa.getRazonSocial());
            empresaDB.setEmail(empresa.getEmail());
            return empresaRepository.save(empresaDB);
        });
    }

    @Override
    public void deleteById(Long id) {
        empresaRepository.deleteById(id);
    }

    @Override
    public void deleteByIdAndUsuarioId(Long id, Long usuarioId) {
        empresaRepository.findByIdAndUsuarioId(id, usuarioId)
                .ifPresent(empresa -> empresaRepository.deleteById(id));
    }
}