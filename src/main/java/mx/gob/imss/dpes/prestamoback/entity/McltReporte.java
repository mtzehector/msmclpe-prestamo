/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "MCLT_REPORTE")
public class McltReporte extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_REPORTE")
    @GenericGenerator(
            name = "MCLS_REPORTE",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_REPORTE"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_REPORTE")
    @Getter
    @Setter
    private Long id;

    @Size(max = 10)
    @Column(name = "PERIODO_NOMINA")
    @Getter
    @Setter
    private String periodoNomina;

    @JoinColumn(name = "CVE_TIPO_REPORTE", referencedColumnName = "CVE_TIPO_REPORTE")
    @Getter
    @Setter
    @OneToOne(optional = false)
    private MclcTipoReporte tipoReporte;

    @JoinColumn(name = "CVE_SUB_TIPO_REPORTE", referencedColumnName = "CVE_SUB_TIPO_REPORTE")
    @Getter
    @Setter
    @OneToOne(optional = false)
    private MclcSubTipoReporte subTipoReporte;

    @JoinColumn(name = "CVE_ESTADO_REPORTE", referencedColumnName = "CVE_ESTADO_REPORTE")
    @Getter
    @Setter
    @OneToOne(optional = false)
    private MclcEstadoReporte estadoReporte;

    @JoinColumn(name = "CVE_ENTIDAD_FINANCIERA", referencedColumnName = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    @OneToOne(optional = false)
    private MclcEntidadFinanciera entidadFinanciera;
}
