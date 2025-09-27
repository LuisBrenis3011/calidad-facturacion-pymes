package com.billtel.calidad.facturacion_pymes.Empresas.repositories;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

}
