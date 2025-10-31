/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "MCLT_PRESTAMO_RECUPERACION")
public class PrestamoRecuperacion extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_PRESTAMO_RECUPERACION")
    @GenericGenerator(
            name = "MCLS_PRESTAMO_RECUPERACION",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PRESTAMO_RECUPERACION"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PRESTAMO_RECUPERACION")
    @Getter
    @Setter
    private Long id;

    @Column(name = "CVE_PRESTAMO")
    @Getter
    @Setter
    private Long prestamo;

    @Column(name = "CVE_SOLICITUD")
    @Getter
    @Setter
    private Long solicitud;

    @Column(name = "NUM_SOLICITUD_SIPRE")
    @Getter
    @Setter
    private String numSolicitudSipre;

    @Column(name = "CAN_MONTO_SOL")
    @Getter
    @Setter
    private BigDecimal canMontoSol;

    @Column(name = "CAN_DESCUENTO_MENSUAL")
    @Getter
    @Setter
    private BigDecimal canDescuentoMensual;

    @Column(name = "IMP_REAL_PRESTAMO")
    @Getter
    @Setter
    private BigDecimal impRealPrestamo;

    @Column(name = "CAN_CAT_PRESTAMO")
    @Getter
    @Setter
    private BigDecimal canCatPrestamo;

    @Column(name = "NUM_PLAZO_PRESTAMO")
    @Getter
    @Setter
    private BigDecimal numPlazoPrestamo;

    @Column(name = "NUM_MES_RECUPERADO")
    @Getter
    @Setter
    private BigDecimal numMesRecuperado;

    @Column(name = "NUM_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private String numEntidadFinanciera;
    
    
    
    @Column(name = "IMP_SUMA_DESCUENTO_MENSUAL")
    @Getter
    @Setter
    private BigDecimal impSumaDescMensual;
    
    @Column(name = "IND_MEJOR_OFERTA")
    @Getter
    @Setter
    private Integer mejorOferta;
    
    @Column(name = "NOM_COMERCIAL_SIPRE")
    @Getter
    @Setter
    private String nombreComercial;

    @Column(name = "CLABE")
    @Getter
    @Setter
    private String clabe;
    
    @Column(name = "MONTO_ACTUALIZADO")
    @Getter
    @Setter
    private Long montoActualizado;
    
    @Column(name = "REFERENCIA")
    @Getter
    @Setter
    private String referencia;
    
    @Column(name = "NUM_FOLIO_SOLICITUD")
    @Getter
    @Setter
    private String numFolioSolicitud;
}
