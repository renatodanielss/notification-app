package br.com.notification.schedule.api.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

@SuppressWarnings({"PlaceholderCountMatchesArgumentCount", "DuplicatedCode"})
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        logger.error("{}", ex);
        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.INTERNAL_SERVER_ERROR);
        responseError.setMessage(String.format("Error: %s", ex.getMessage()));
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());

        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HandlerMethod handlerMethod, WebRequest request) {
        this.logger = LogManager.getLogger(handlerMethod.getMethod().getDeclaringClass());
        this.logger.error("{}", ex);
        BindingResult result = ex.getBindingResult();

        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.BAD_REQUEST);
        responseError.setMessage(String.format("Validation failed for object='%s'. Error count: %d",
                result.getObjectName(), result.getErrorCount()));
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());

        for (FieldError error : result.getFieldErrors()) {
            responseError.getErrors().add(new ValidationError(error.getField(), error.getDefaultMessage()));
        }

        for (ObjectError globalError : result.getGlobalErrors()) {
            responseError.getErrors().add(new ValidationError("global", globalError.getDefaultMessage()));
        }

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleValidationFailed(ValidationFailedException ex, HandlerMethod handlerMethod, WebRequest request) {
        this.logger = LogManager.getLogger(handlerMethod.getMethod().getDeclaringClass());
        this.logger.error("{}", ex);

        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.BAD_REQUEST);
        responseError.setMessage(ex.getMessage());
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());

        for (ValidationError validationError : ex.getErrors()) {
            responseError.getErrors().add(validationError);
        }

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, HandlerMethod handlerMethod, WebRequest request) {
        this.logger = LogManager.getLogger(handlerMethod.getMethod().getDeclaringClass());
        this.logger.error("{}", ex);

        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.BAD_REQUEST);
        responseError.setMessage(String.format("Illegal argument error: %s", ex.getMessage()));
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());
        responseError.getErrors().add(new ValidationError("Illegal", ex.getMessage()));

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ResponseEntity<Object> handleEntityNotFound(DocumentNotFoundException ex, HandlerMethod handlerMethod, WebRequest request) {
        this.logger = LogManager.getLogger(handlerMethod.getMethod().getDeclaringClass());
        this.logger.error("{}", ex);

        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.NOT_FOUND);
        responseError.setMessage(String.format("Document not found error: %s", ex.getMessage()));
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());
        responseError.getErrors().add(new ValidationError("Document", ex.getMessage()));

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    @ResponseBody
    protected ResponseEntity<Object> handleGatewayException(GatewayException ex, HandlerMethod handlerMethod, WebRequest request) {
        this.logger = LogManager.getLogger(handlerMethod.getMethod().getDeclaringClass());
        this.logger.error("{}", ex);

        DefaultResponseError responseError = new DefaultResponseError(HttpStatus.BAD_GATEWAY);
        responseError.setMessage(String.format("Bad gateway error: %s", ex.getMessage()));
        responseError.setException(ex.getClass().getCanonicalName());
        responseError.setPath(((ServerHttpRequest) request).getURI().getPath());
        responseError.getErrors().add(new ValidationError("Gateway", ex.getMessage()));

        return new ResponseEntity<>(responseError, HttpStatus.BAD_GATEWAY);
    }
}
