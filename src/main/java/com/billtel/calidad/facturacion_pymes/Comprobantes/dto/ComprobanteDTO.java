package com.billtel.calidad.facturacion_pymes.Comprobantes.dto;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ComprobanteDTO {

    private Long id;
    private Long empresaId;
    private String nroDocCliente;
    private String direccionCliente;
    private String serie;
    private Integer correlativo;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal igvTotal;
    private BigDecimal total;
    private String estadoSunat;
    private List<DetalleComprobanteDTO> detalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getNroDocCliente() {
        return nroDocCliente;
    }

    public void setNroDocCliente(String nroDocCliente) {
        this.nroDocCliente = nroDocCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIgvTotal() {
        return igvTotal;
    }

    public void setIgvTotal(BigDecimal igvTotal) {
        this.igvTotal = igvTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstadoSunat() {
        return estadoSunat;
    }

    public void setEstadoSunat(String estadoSunat) {
        this.estadoSunat = estadoSunat;
    }

    public List<DetalleComprobanteDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleComprobanteDTO> detalles) {
        this.detalles = detalles;
    }
}
