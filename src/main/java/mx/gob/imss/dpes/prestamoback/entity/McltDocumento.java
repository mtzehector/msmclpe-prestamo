/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author andrea.cervantes
 */
@Entity
@Table(name = "MCLT_DOCUMENTO")
public class McltDocumento extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_DOCUMENTO")
    @Getter
    @Setter
    @GeneratedValue(generator = "SEQ_GEN_MCLS_DOCUMENTO")
    @GenericGenerator(
            name = "SEQ_GEN_MCLS_DOCUMENTO",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_DOCUMENTO")
                ,
        @Parameter(name = "initial_value", value = "1")
                ,
        @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_SOLICITUD")
    @Getter
    @Setter
    private Long cveSolicitud;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_TIPO_DOCUMENTO")
    @Getter
    @Setter
    private Long tipoDocumento;
    
    @Column(name = "CVE_PERSONA")
    @Getter
    @Setter
    private Long cvePersona;
    
    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private Long cveEntidadFinanciera;
    
    @Column(name = "CVE_PRESTAMO_RECUPERACION")
    @Getter
    @Setter
    private Long cvePrestamoRecuperacion;
    
    @Size(max = 1500)
    @Column(name = "REF_SELLO")
    @Getter
    @Setter
    private String refSello;
    @Size(max = 4000)
    @Column(name = "REF_DOCUMENTO")
    @Getter
    @Setter
    private String refDocumento;
    @Size(max = 100)
    @Column(name = "REF_DOC_BOVEDA")
    @Getter
    @Setter
    private String refDocBoveda;
    
    @Column(name = "IND_DOCUMENTO_HISTORICO")
    @Getter
    @Setter
    private boolean indDocumentoHistorico;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.cveSolicitud);
        hash = 29 * hash + Objects.hashCode(this.tipoDocumento);
        hash = 29 * hash + Objects.hashCode(this.cvePersona);
        hash = 29 * hash + Objects.hashCode(this.cveEntidadFinanciera);
        hash = 29 * hash + Objects.hashCode(this.cvePrestamoRecuperacion);
        hash = 29 * hash + Objects.hashCode(this.refSello);
        hash = 29 * hash + Objects.hashCode(this.refDocumento);
        hash = 29 * hash + Objects.hashCode(this.refDocBoveda);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final McltDocumento other = (McltDocumento) obj;
        if (!Objects.equals(this.refSello, other.refSello)) {
            return false;
        }
        if (!Objects.equals(this.refDocumento, other.refDocumento)) {
            return false;
        }
        if (!Objects.equals(this.refDocBoveda, other.refDocBoveda)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.cveSolicitud, other.cveSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.tipoDocumento, other.tipoDocumento)) {
            return false;
        }
        if (!Objects.equals(this.cvePersona, other.cvePersona)) {
            return false;
        }
        if (!Objects.equals(this.cveEntidadFinanciera, other.cveEntidadFinanciera)) {
            return false;
        }
        if (!Objects.equals(this.cvePrestamoRecuperacion, other.cvePrestamoRecuperacion)) {
            return false;
        }
        return true;
    }
    


}
