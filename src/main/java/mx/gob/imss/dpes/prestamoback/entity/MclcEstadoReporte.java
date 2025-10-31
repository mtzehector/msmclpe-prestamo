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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;

/**
 *
 * @author juan.garfias
 */
@Entity
@Table(name = "MCLC_ESTADO_REPORTE")
public class MclcEstadoReporte extends LogicDeletedEntity<Long> {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_ESTADO_REPORTE")
    @Getter
    @Setter
    private Long id;

    @Size(max = 100)
    @Column(name = "DES_ESTADO_REPORTE")
    @Getter
    @Setter
    private String desEstadoReporte;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.desEstadoReporte);
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
        final MclcEstadoReporte other = (MclcEstadoReporte) obj;
        if (!Objects.equals(this.desEstadoReporte, other.desEstadoReporte)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
