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
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "MCLT_PRESTAMO_INSUMO_TA")
public class AmortizacionDatosBase extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_PRESTAMO_INSUMO_TA")
    @GenericGenerator(
            name = "MCLS_PRESTAMO_INSUMO_TA",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PRESTAMO_INSUMO_TA"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PRESTAMO_INSUMOS_TA")
    @Getter
    @Setter
    private Long id;

    @Column(name = "CVE_SOLICITUD")
    @Getter
    @Setter
    private Long cveSolicitud;

    @Column(name = "PLAZO")
    @Getter
    @Setter
    private Integer plazo;

    @Column(name = "CAT")
    @Getter
    @Setter
    private Double cat;

    @Column(name = "MONTO")
    @Getter
    @Setter
    private BigDecimal importeMontoSolicitado;

    @Column(name = "DESC_MENSUAL")
    @Getter
    @Setter
    private BigDecimal importeDescuentoMensual;

    @Column(name = "TOTAL_A_PAGAR")
    @Getter
    @Setter
    private BigDecimal importeTotalPagar;

    @Column(name = "FOLIO_SIPRE")
    @Getter
    @Setter
    private String folioSipre;

    @Column(name = "CVE_TIPO_SIMULACION")
    @Getter
    @Setter
    private Long cveTipoSimulacion;

    @Column(name = "HISTORICO")
    @Getter
    @Setter
    private Integer historico;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Getter
    @Setter
    @Transient
    private Integer test;

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmortizacionDatosBase)) {
            return false;
        }
        AmortizacionDatosBase other = (AmortizacionDatosBase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
