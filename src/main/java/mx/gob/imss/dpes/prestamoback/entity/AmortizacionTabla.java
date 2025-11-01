/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author edgar.arenas
 */
@Entity
@Table(name = "MCLT_PRESTAMO_TA")
public class AmortizacionTabla extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_PRESTAMO_TA")
    @GenericGenerator(
            name = "MCLS_PRESTAMO_TA",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PRESTAMO_TA"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PRESTAMO_TA")
    @Getter
    @Setter
    private Long id;

    @JoinColumn(name = "CVE_PRESTAMO_INSUMOS_TA", referencedColumnName = "CVE_PRESTAMO_INSUMOS_TA")
     @ManyToOne (optional = false)
     @Getter @Setter private AmortizacionDatosBase amortizacionDatosBase;

    @Column(name = "PERIODO")
    @Getter
    @Setter
    private Integer periodo;

    @Column(name = "SALDO")
    @Getter
    @Setter
    private Double saldo;
    
    @Column(name = "CAPITAL")
    @Getter
    @Setter
    private Double capital;
    
    @Column(name = "INTERESES")
    @Getter
    @Setter
    private Double intereses;
    
    @Column(name = "IVA")
    @Getter
    @Setter
    private Double iva;
    
    @Column(name = "DESCUENTO")
    @Getter
    @Setter
    private Double descuento;

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmortizacionTabla)) {
            return false;
        }
        AmortizacionTabla other = (AmortizacionTabla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
