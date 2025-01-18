package com.alura.foro_api.infra.errores;

import java.io.Serial;

public class IntegrityValidation extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public IntegrityValidation(String message) {
        super(message);
    }
}