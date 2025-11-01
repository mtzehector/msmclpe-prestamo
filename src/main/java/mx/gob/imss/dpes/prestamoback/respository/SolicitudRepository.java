/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.McltSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juanf.barragan
 */
public interface SolicitudRepository extends JpaRepository<McltSolicitud, Long>, JpaSpecificationExecutor<McltSolicitud>{
    
    McltSolicitud findByNumFolioSolicitud(String numFolioSolicitud);
}
