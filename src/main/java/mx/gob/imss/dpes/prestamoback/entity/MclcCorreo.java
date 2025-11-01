/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

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
@Table(name = "MCLC_CORREO")
public class MclcCorreo extends LogicDeletedEntity<Long> {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_CORREO")
    @Getter
    @Setter
    private Long id;

    @Size(max = 300)
    @Column(name = "CORREO")
    @Getter
    @Setter
    private String correo;

    @NotNull
    @Column(name = "CORREO_ACTIVO")
    @Getter
    @Setter
    private Long correoActivo;

}
