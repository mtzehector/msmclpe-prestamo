package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.McltPrestamoInsumoTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface PrestamoInsumoTaRepository extends JpaRepository<McltPrestamoInsumoTa, Long>, JpaSpecificationExecutor<McltPrestamoInsumoTa> {

    McltPrestamoInsumoTa findByCveSolicitud(@Param("cveSolicitud") Long cveSolicitud);

}
