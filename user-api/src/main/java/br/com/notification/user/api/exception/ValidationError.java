package br.com.notification.user.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

    @Getter
    private String field;

    @Getter
    private String message;

    @SuppressWarnings("unused")
    public ValidationError setField(String field) {
        this.field = field;
        return this;
    }

    public ValidationError setMessage(String message) {
        this.message = message;
        return this;
    }
}