package br.com.notification.user.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GatewayException extends RuntimeException {

    protected String message;

    public GatewayException(Exception e) {
        super(e);
    }
}
