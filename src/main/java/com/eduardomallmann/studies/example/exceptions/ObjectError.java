package com.eduardomallmann.studies.example.exceptions;

import com.eduardomallmann.studies.example.utils.MessageUtils;

/**
 * Data transfer object used to reply http requests with error.
 *
 * @author eduardomallmann
 */
public record ObjectError(String field,
                   String exception,
                   String message) {
    public ObjectError(final String key, final String exception) {
        this(MessageUtils.getMessage(key.substring(0, key.lastIndexOf('.'))), MessageUtils.getMessage(key), exception);
    }
}
