package br.com.notification.schedule.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNotFoundException extends RuntimeException {

    protected String message;

    public DocumentNotFoundException(Exception e) {
        super(e);
    }
}
