/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author antonio
 */
@javax.persistence.Entity
@Table(name = "MCLC_PLAZO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plazo implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "CVE_PLAZO")
  @Getter @Setter private Integer clave;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "DES_PLAZO")
  @Getter @Setter private String descripcion;  
  
  
}
