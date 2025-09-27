package com.billtel.calidad.facturacion_pymes.Productos.repositories;

import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Producto, Long> {

}
