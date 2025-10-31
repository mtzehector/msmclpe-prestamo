/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author juan.garfias
 */
@Entity
@Table(name = "MCLT_REPORTE_DOCUMENTO")
public class McltReporteDocumento extends LogicDeletedEntity<Long> {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_REPORTE_DOCUMENTO")
    @GenericGenerator(
            name = "MCLS_REPORTE_DOCUMENTO",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_REPORTE_DOCUMENTO"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_REPORTE_DOCUMENTO")
    @Getter
    @Setter
    private Long id;

    @Column(name = "CVE_REPORTE")
    @Getter
    @Setter
    private Long cveReporte;

    @JoinColumn(name = "CVE_DOCUMENTO", referencedColumnName = "CVE_DOCUMENTO")
    @Getter
    @Setter
    @OneToOne(optional = false)
    private McltDocumento documento;

}
