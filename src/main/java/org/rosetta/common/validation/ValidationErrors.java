package org.rosetta.common.validation;

import org.rosetta.common.exception.ValidationException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class ValidationErrors {
    private final Map<String, List<String>> errors = new HashMap<>();

    public void add(String field, String message) {
        errors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
    }

    public void throwIfAny() {
        if(!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
