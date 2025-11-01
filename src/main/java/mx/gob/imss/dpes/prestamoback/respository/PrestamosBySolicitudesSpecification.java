/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import java.util.Iterator;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.prestamoback.endpoint.ConsultaPrestamosVigentesEndPoint;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo_;
import mx.gob.imss.dpes.prestamoback.model.PrestamoBySolicitudRequest;
import mx.gob.imss.dpes.prestamoback.model.PrestamosBySolicitudesRequest;

/**
 *
 * @author antonio
 */
public class PrestamosBySolicitudesSpecification extends BaseSpecification<Prestamo> {

  private final PrestamosBySolicitudesRequest solicitudes;

  public PrestamosBySolicitudesSpecification(PrestamosBySolicitudesRequest solicitudes) {
    this.solicitudes = solicitudes;
  }

  @Override
  public Predicate toPredicate(Root<Prestamo> root, CriteriaQuery<?> cq,
          CriteriaBuilder cb) {
    
    return root.get(Prestamo_.solicitud).in(solicitudes.getSolicitudes());
    
  }

}
