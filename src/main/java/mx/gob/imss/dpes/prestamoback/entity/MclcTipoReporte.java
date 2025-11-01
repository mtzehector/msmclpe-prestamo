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
@Table(name = "MCLC_TIPO_REPORTE")
public class MclcTipoReporte extends LogicDeletedEntity<Long> {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_TIPO_REPORTE")
    @Getter
    @Setter
    private Long id;

    @Size(max = 100)
    @Column(name = "DES_TIPO_REPORTE")
    @Getter
    @Setter
    private String desTipoReporte;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.desTipoReporte);
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
        final MclcTipoReporte other = (MclcTipoReporte) obj;
        if (!Objects.equals(this.desTipoReporte, other.desTipoReporte)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
