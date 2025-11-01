/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 *
 * @author antonio
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamosVigentesPersistenceService extends BaseCRUDService<Prestamo, Prestamo, Long, Long>{

  @Autowired
  private PrestamoRepository repository;
  
  

  @Override
  public JpaSpecificationExecutor<Prestamo> getRepository() {
    return repository;
  }

  @Override
  public JpaRepository<Prestamo, Long> getJpaRepository() {
    return repository;
  }
  
  
}
