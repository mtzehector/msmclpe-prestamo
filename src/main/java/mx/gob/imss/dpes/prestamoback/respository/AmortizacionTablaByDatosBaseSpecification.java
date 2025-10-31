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
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase_;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla_;

/**
 *
 * @author Diego Velazquez
 */
public class AmortizacionTablaByDatosBaseSpecification extends BaseSpecification<AmortizacionTabla> {

    private final Long idDatosBase;
    public AmortizacionTablaByDatosBaseSpecification(Long idEntidadFinanciera) {
        this.idDatosBase = idEntidadFinanciera;
    }

    @Override
    public Predicate toPredicate(Root<AmortizacionTabla> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
      
      return cb.equal(root.join(AmortizacionTabla_.amortizacionDatosBase).get(
             AmortizacionDatosBase_.id ), this.idDatosBase );
      
    }

}
