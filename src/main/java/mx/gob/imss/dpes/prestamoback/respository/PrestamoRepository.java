/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author antonio
 */
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {

    @Query("SELECT p FROM Prestamo p WHERE p.solicitud IN(:ids) ORDER BY p.id")
    List<Prestamo> buscarPrestamosPorIdSolicitud(@Param("ids") List<Long> idSolicitudList);

}

