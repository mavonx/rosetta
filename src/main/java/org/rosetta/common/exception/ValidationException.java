package org.rosetta.common.exception;

import java.util.Map;
import java.util.List;
import java.util.Collections;

public class ValidationException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public ValidationException(Map<String, List<String>> errors) {
        super("One or more validation errors occurred");
        this.errors = Collections.unmodifiableMap(errors);
    }

    public ValidationException(String field, String message) {
        this(Map.of(field, List.of(message)));
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
