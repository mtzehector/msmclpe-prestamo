package mx.gob.imss.dpes.prestamoback.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class ConsultarDocumentosException extends BusinessException {

    private static final String KEY ="err001";

    public ConsultarDocumentosException() {
        super(KEY);
    }
}
