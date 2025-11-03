package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ComprobanteRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ComprobanteServiceImpl implements IComprobanteService {

    private final ComprobanteRepository comprobanteRepository;
    private final EmpresaRepository empresaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<Comprobante> findAll() {
        return StreamSupport.stream(comprobanteRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public List<Comprobante> findByEmpresaId(Long empresaId) {
        return comprobanteRepository.findByEmpresaId(empresaId);
    }

    @Override
    public Optional<Comprobante> findById(Long id) {
        return comprobanteRepository.findById(id);
    }

    @Override
    public Optional<Comprobante> findByIdAndEmpresaId(Long id, Long empresaId) {
        return comprobanteRepository.findByIdAndEmpresaId(id, empresaId);
    }

    @Override
    public Comprobante save(Comprobante comprobante) {
        return comprobanteRepository.save(comprobante);
    }

    @Override
    public void deleteById(Long id) {
        comprobanteRepository.deleteById(id);
    }

    @Override
    public Integer getNextCorrelativo(Long empresaId, String serie) {
        return comprobanteRepository
                .findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(empresaId, serie)
                .map(c -> c.getCorrelativo() + 1)
                .orElse(1);
    }

    @Override
    public Comprobante createComprobante(Comprobante comprobanteBase, Long empresaId, List<DetalleComprobante> detalles) {
        var empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        comprobanteBase.setEmpresa(empresa);

        Integer correlativo = getNextCorrelativo(empresaId, comprobanteBase.getSerie());
        comprobanteBase.setCorrelativo(correlativo);

        BigDecimal subtotalTotal = BigDecimal.ZERO;
        BigDecimal igvTotalTotal = BigDecimal.ZERO;
        BigDecimal totalTotal = BigDecimal.ZERO;

        for (DetalleComprobante detalle : detalles) {
            var producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            detalle.setComprobante(comprobanteBase);
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getValorUnitario());

            BigDecimal subtotal = producto.getValorUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            BigDecimal igv = subtotal.multiply(producto.getIgv()).divide(BigDecimal.valueOf(100));
            BigDecimal total = subtotal.add(igv);

            detalle.setSubtotal(subtotal);
            detalle.setIgv(igv);
            detalle.setTotal(total);

            subtotalTotal = subtotalTotal.add(subtotal);
            igvTotalTotal = igvTotalTotal.add(igv);
            totalTotal = totalTotal.add(total);
        }

        comprobanteBase.setSubtotal(subtotalTotal);
        comprobanteBase.setIgvTotal(igvTotalTotal);
        comprobanteBase.setTotal(totalTotal);
        comprobanteBase.setDetalles(detalles);
        comprobanteBase.setFechaEmision(LocalDateTime.now());
        comprobanteBase.setEstadoSunat(Comprobante.EstadoSunat.PENDIENTE);
        log.info(">>> Correlativo generado: " + comprobanteBase.getCorrelativo());
        log.info(">>> Serie: " + comprobanteBase.getSerie());

        return comprobanteRepository.save(comprobanteBase);
    }

}