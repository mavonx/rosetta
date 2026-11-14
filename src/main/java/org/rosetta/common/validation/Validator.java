package org.rosetta.common.validation;

public interface Validator<T> {
    void validate(T request);
}
