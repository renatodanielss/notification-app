package br.com.notification.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DefaultResponseError {

    @Setter
    private Date timestamp = new Date();
    private Integer status;
    private String error;
    private String path;
    private String message;
    private String exception;

    private List<Object> errors = new ArrayList<>();

    @JsonIgnore
    private HttpStatus httpStatus;

    public DefaultResponseError(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }

    public DefaultResponseError setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public DefaultResponseError setError(String error) {
        this.error = error;
        return this;
    }

    public DefaultResponseError setException(String exception) {
        this.exception = exception;
        return this;
    }

    public DefaultResponseError setMessage(String message) {
        this.message = message;
        return this;
    }

    public DefaultResponseError setPath(String path) {
        this.path = path;
        return this;
    }

    @SuppressWarnings("unused")
    public DefaultResponseError setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    @SuppressWarnings("unused")
    public DefaultResponseError setErrors(List<Object> errors) {
        this.errors = errors;
        return this;
    }
}