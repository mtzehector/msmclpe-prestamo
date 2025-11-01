/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion_;

/**
 *
 * @author salvador.pocteco
 */
public class PrestamoRecuperacionBySolicitudSpecification  extends BaseSpecification<PrestamoRecuperacion> {

    private final Long solicitud;
    
    public PrestamoRecuperacionBySolicitudSpecification(Long solicitud) {
        this.solicitud = solicitud;
    }
    
    @Override
    public Predicate toPredicate(Root<PrestamoRecuperacion> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
         return cb.equal(root.get(PrestamoRecuperacion_.solicitud), this.solicitud);  
    }
    
}
