package com.eduardomallmann.studies.example.utils;

/**
 * Enuns responsáveis pelas mensagens do sistema.
 *
 * @author eduardomallmann
 * @since 0.0.1
 */
public enum PropertiesConstants {

    WRONG_CEP_FIELD("error.address.cep"),
    WRONG_CEP_NUMBER("error.address.cep.invalid");

    private final String errorKey;

    /**
     * Construtor principal.
     *
     * @param errorKey chave de das mensagens
     */
    PropertiesConstants(final String errorKey) {
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
