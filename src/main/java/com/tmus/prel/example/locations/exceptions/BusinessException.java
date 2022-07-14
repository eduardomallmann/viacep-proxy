package com.tmus.prel.example.locations.exceptions;

/**
 * Class responsible for handle the application logic behaviours failure.
 *
 * @author eduardomallmann
 * @since 0.0.1
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 2037470474991303480L;

    private final ObjectError objectError;

    /**
     * Main constructor, creates an error message and converts it into a system error message.
     * <p>It receives a i18n key related to a business error message and the original error message</p>
     *
     * @param key   i18n key
     * @param error system error message
     */
    public BusinessException(final String key, final String error) {
        super(error);
        this.objectError = new ObjectError(key, error);
    }

    public ObjectError getObjectError() {
        return objectError;
    }
}
