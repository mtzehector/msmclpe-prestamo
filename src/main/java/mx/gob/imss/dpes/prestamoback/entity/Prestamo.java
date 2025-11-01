/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import mx.gob.imss.dpes.support.config.CustomDateDeserializer;
import mx.gob.imss.dpes.support.config.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author eduardo.loyo
 */
@Entity
@Table(name = "MCLT_PRESTAMO")
public class Prestamo extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "SEQ_GEN_PRESTAMO")
    @GenericGenerator(
            name = "SEQ_GEN_PRESTAMO",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PRESTAMO"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PRESTAMO")
    @Getter
    @Setter
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMP_MONTO_SOL")
    @Getter
    @Setter
    private BigDecimal monto;
    @Column(name = "IMP_DESCUENTO_NOMINA")
    @Getter
    @Setter
    private BigDecimal impDescNomina;
    @Column(name = "IMP_TOTAL_PAGAR")
    @Getter
    @Setter
    private BigDecimal impTotalPagar;
    @Column(name = "FEC_PRIMER_DESCUENTO")
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date primerDescuento;
    @Column(name = "NUM_PERIODO_NOMINA")
    @Getter
    @Setter
    private Integer numPeriodoNomina;
    @Size(max = 18)
    @Column(name = "REF_CUENTA_CLABE")
    @Getter
    @Setter
    private String refCuentaClabe;
    @Column(name = "CVE_CONDICION_OFERTA")
    @Getter
    @Setter
    @NotNull
    private Long idOferta;
    @Column(name = "CVE_PROMOTOR")
    @Getter
    @Setter
    private Long promotor;
    @Column(name = "CVE_TIPO_SIMULACION")
    @Getter
    @Setter
    private Long tipoSimulacion;
    @Column(name = "CVE_TIPO_CREDITO")
    @Getter
    @Setter
    private Long tipoCredito;
    @Column(name = "CVE_SOLICITUD")
    @Getter
    @Setter
    private Long solicitud;

}
