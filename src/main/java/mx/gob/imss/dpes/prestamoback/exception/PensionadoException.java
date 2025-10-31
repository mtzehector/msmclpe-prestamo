package mx.gob.imss.dpes.prestamoback.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class PensionadoException extends BusinessException {

    public final static String ERROR_AL_INVOCAR_SERVICIOS_SISTRAP = "err008";
    public PensionadoException(String messageKey) {
        super(messageKey);
    }
}
