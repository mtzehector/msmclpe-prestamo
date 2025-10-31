package mx.gob.imss.dpes.prestamoback.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class OfertaException extends BusinessException {
    private final static String KEY = "err002";

    public OfertaException() {
        super(KEY);
    }
}
