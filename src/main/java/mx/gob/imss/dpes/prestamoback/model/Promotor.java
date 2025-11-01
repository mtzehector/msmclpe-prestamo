/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.enums.SexoEnum;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Delegacion;
import mx.gob.imss.dpes.common.model.EntidadFederativa;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.EntidadFinanciera;
import mx.gob.imss.dpes.support.config.CustomDateDeserializer;
import mx.gob.imss.dpes.support.config.CustomDateSerializer;

/**
 *
 * @author edgar.arenas
 */
@Data
public class Promotor extends BaseModel{
    
   @Getter @Setter EntidadFinanciera entidadFinanciera;
   @Getter @Setter Long id;
   @Getter @Setter String numEmpleado;    
   @Getter @Setter Long cveEntidadFinanciera;
   @Getter @Setter String nss;
  @Getter @Setter String grupoFamiliar;
  @Getter @Setter String curp;
  @Getter @Setter Delegacion delegacion = new Delegacion();
  @Getter @Setter String subDelegacion;
  @Getter @Setter EntidadFederativa entidadFederativa = new EntidadFederativa();
  @Getter @Setter String cuentaClabe;
  @JsonDeserialize( using = CustomDateDeserializer.class )
  @JsonSerialize(using = CustomDateSerializer.class) @Getter @Setter Date fechaNacimiento;
  @Getter @Setter String tipoPension;
   @Getter @Setter String nombre;
  @Getter @Setter String primerApellido;
  @Getter @Setter String segundoApellido;
  @Getter @Setter String correoElectronico;
  @Getter @Setter String telefono;
  @Getter @Setter Integer idSexo;
  @Getter @Setter Integer edad;
  @Getter @Setter Integer idPersonaEF;
  @Getter @Setter Sexo sexo;  
}
