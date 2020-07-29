package br.com.rhribeiro25.manageLabs.error;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Builder
@Getter
public class ValidationErrorDetails implements Serializable {
    private final String message;
    private final Object parameter;
}
