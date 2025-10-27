package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper.ComprobanteDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper.ComprobanteRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComprobanteFacade implements IComprobanteFacade {

    private final IComprobanteService comprobanteService;
    private final ComprobanteRequestMapper comprobanteRequestMapper;
    private final ComprobanteDtoMapper comprobanteDtoMapper;

    @Override
    public List<ComprobanteDto> findAll() {
        return comprobanteService.findAll().stream()
                .map(comprobanteDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComprobanteDto> findByEmpresaId(Long empresaId) {
        return comprobanteService.findByEmpresaId(empresaId).stream()
                .map(comprobanteDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ComprobanteDto> findById(Long id) {
        return comprobanteService.findById(id)
                .map(comprobanteDtoMapper::toDto);
    }

    @Override
    public Optional<ComprobanteDto> findByIdAndEmpresaId(Long id, Long empresaId) {
        return comprobanteService.findByIdAndEmpresaId(id, empresaId)
                .map(comprobanteDtoMapper::toDto);
    }

    @Override
    public ComprobanteDto create(ComprobanteRequest request) {
        var comprobante = comprobanteRequestMapper.toDomain(request);

        var detalles = request.getDetalles().stream()
                .map(detalleReq -> {
                    DetalleComprobante detalle = new DetalleComprobante();
                    Producto producto = new Producto();
                    producto.setId(detalleReq.getProductoId());
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleReq.getCantidad());
                    return detalle;
                })
                .collect(Collectors.toList());

        // 🔥 Aquí está la diferencia:
        var comprobanteCreado = comprobanteService.createComprobante(
                comprobante,
                request.getEmpresaId(),
                detalles
        );

        return comprobanteDtoMapper.toDto(comprobanteCreado);
    }

    @Override
    public void deleteById(Long id) {
        comprobanteService.deleteById(id);
    }
}
