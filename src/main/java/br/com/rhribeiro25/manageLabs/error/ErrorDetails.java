package br.com.rhribeiro25.manageLabs.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Builder
@Getter
public class ErrorDetails implements Serializable {
    private final String status;
    private final int code;
    private final String message;
    private final Long timesTamp;
    private final String objectName;
    private final Map<String, ValidationErrorDetails> errors;
}

