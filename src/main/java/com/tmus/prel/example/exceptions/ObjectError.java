package com.tmus.prel.example.exceptions;

import com.tmus.prel.example.utils.MessageUtils;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectError implements Serializable {

    private static final long serialVersionUID = -618490719017480447L;

    private String field;
    private String exception;
    private String message;

    public ObjectError(final String key, final String exception) {
        this.field = MessageUtils.getMessage(key.substring(0, key.lastIndexOf('.')));
        this.message = MessageUtils.getMessage(key);
        this.exception = exception;
    }
}
