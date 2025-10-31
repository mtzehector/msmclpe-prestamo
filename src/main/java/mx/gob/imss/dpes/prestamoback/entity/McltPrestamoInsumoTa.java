package mx.gob.imss.dpes.prestamoback.entity;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "MCLT_PRESTAMO_INSUMO_TA")
public class McltPrestamoInsumoTa extends LogicDeletedEntity<Long> {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "SEQ_GEN_PRESTAMO_INSUMO_TA")
    @GenericGenerator(
            name = "SEQ_GEN_PRESTAMO_INSUMO_TA",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "MCLS_PRESTAMO_INSUMO_TA"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PRESTAMO_INSUMOS_TA")
    @Getter
    @Setter
    private Long id;

    @Column(name = "HISTORICO")
    @Getter
    @Setter
    private Integer historico;

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
    private BigDecimal cat;

    @Column(name = "MONTO")
    @Getter
    @Setter
    private BigDecimal monto;

    @Column(name = "TOTAL_A_PAGAR")
    @Getter
    @Setter
    private BigDecimal totalPagar;

    @Column(name = "CVE_TIPO_SIMULACION")
    @Getter
    @Setter
    private Integer cveTipoSimulacion;

    @Column(name = "FOLIO_SIPRE")
    @Getter
    @Setter
    private String folioSipre;

    @Column(name = "DESC_MENSUAL")
    @Getter
    @Setter
    private BigDecimal descuentoMensual;

}
