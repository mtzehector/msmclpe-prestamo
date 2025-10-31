package mx.gob.imss.dpes.prestamoback.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

public class DelegacionModel extends BaseModel {
    @Getter
    @Setter
    Long id;
    @Getter
    @Setter
    String numDelegacion;
    @Getter
    @Setter
    String desDelegacion;
}
