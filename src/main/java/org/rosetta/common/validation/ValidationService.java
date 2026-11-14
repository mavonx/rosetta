package org.rosetta.common.validation;

import org.rosetta.common.exception.ValidationException;

import java.util.Map;
import java.util.HashMap;

public class ValidationService {
    private final Map<Class<?>, Validator<?>> validators = new HashMap<>();

    public <T> void register(Class<T> type, Validator<T> validator) {
        validators.put(type, validator);
    }

    @SuppressWarnings("unchecked")
    public <T> void validate(T request) {
        if(request == null)
            throw new ValidationException("request", "Request is required");

        Validator<T> validator = (Validator<T>) validators.get(request.getClass());
        if(validator != null)
            validator.validate(request);
    }
}
