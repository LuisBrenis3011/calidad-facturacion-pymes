package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresa_mapper.EmpresaDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresa_mapper.EmpresaRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaFacadeTest {

    @Mock
    private IEmpresaService empresaService;
    @Mock
    private EmpresaRequestMapper empresaRequestMapper;
    @Mock
    private EmpresaDtoMapper empresaDtoMapper;

    @InjectMocks
    private EmpresaFacade empresaFacade;

    private Empresa empresa;
    private EmpresaDto empresaDto;
    private EmpresaRequest empresaRequest;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setId(1L);
        empresa.setRuc("123456789");
        empresa.setRazonSocial("Billtel SAC");

        empresaDto = new EmpresaDto();
        empresaDto.setId(1L);
        empresaDto.setRuc("123456789");
        empresaDto.setRazonSocial("Billtel SAC");

        empresaRequest = new EmpresaRequest();
        empresaRequest.setRuc("123456789");
        empresaRequest.setRazonSocial("Billtel SAC");
        empresaRequest.setUsername("brenis");
    }

    @Test
    void testFindAll_ShouldReturnMappedList() {
        when(empresaService.findAll()).thenReturn(List.of(empresa));
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        List<EmpresaDto> result = empresaFacade.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRuc()).isEqualTo("123456789");
        verify(empresaService).findAll();
    }

    @Test
    void testFindByUsuarioId_ShouldReturnMappedList() {
        when(empresaService.findByUsuarioId(10L)).thenReturn(List.of(empresa));
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        List<EmpresaDto> result = empresaFacade.findByUsuarioId(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRazonSocial()).isEqualTo("Billtel SAC");
    }

    @Test
    void testFindById_ShouldReturnMappedOptional() {
        when(empresaService.findById(1L)).thenReturn(Optional.of(empresa));
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        Optional<EmpresaDto> result = empresaFacade.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testCreate_ShouldMapAndReturnDto() {
        when(empresaRequestMapper.toDomain(empresaRequest)).thenReturn(empresa);
        when(empresaService.save("brenis", empresa)).thenReturn(empresa);
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        EmpresaDto result = empresaFacade.create(empresaRequest);

        assertThat(result.getRuc()).isEqualTo("123456789");
        verify(empresaService).save("brenis", empresa);
    }

    @Test
    void testDeleteById_ShouldCallService() {
        empresaFacade.deleteById(1L);
        verify(empresaService).deleteById(1L);
    }

    @Test
    void testDeleteByIdAndUsuarioId_ShouldCallService() {
        empresaFacade.deleteByIdAndUsuarioId(1L, 10L);
        verify(empresaService).deleteByIdAndUsuarioId(1L, 10L);
    }

    @Test
    void testFindByUsername_ShouldReturnMappedList() {
        when(empresaService.findByUsername("brenis")).thenReturn(Arrays.asList(empresa));
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        List<EmpresaDto> result = empresaFacade.findByUsername("brenis");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRazonSocial()).isEqualTo("Billtel SAC");
    }

    @Test
    void testUpdate_ShouldMapRequestAndReturnMappedOptional() {
        when(empresaRequestMapper.toDomain(empresaRequest)).thenReturn(empresa);
        when(empresaService.update(empresa, 1L)).thenReturn(Optional.of(empresa));
        when(empresaDtoMapper.toDto(empresa)).thenReturn(empresaDto);

        Optional<EmpresaDto> result = empresaFacade.update(empresaRequest, 1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }
}
