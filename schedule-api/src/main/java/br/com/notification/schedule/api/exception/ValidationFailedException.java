package br.com.notification.schedule.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailedException extends RuntimeException {

    @Getter
    protected String message;

    @Getter
    @Setter
    protected List<ValidationError> errors = new ArrayList<>();

    public ValidationFailedException() {
    }

    public ValidationFailedException(String message) {
        this.message = message;
    }

    public ValidationFailedException(List<ValidationError> errors) {
        String[] fields = errors.stream().map(e -> e.getField()).toArray(String[]::new);
        this.message = String.format("Validation failed for fields: %s", StringUtils.join(fields, ", "));
        this.errors = errors;
    }

    public ValidationFailedException(String message, List<ValidationError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ValidationFailedException(Exception e) {
        super(e);
    }
}
