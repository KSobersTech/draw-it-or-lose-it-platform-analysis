package tech.ksobers.drawit.api;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import tech.ksobers.drawit.domain.DuplicateNameException;
import tech.ksobers.drawit.domain.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final URI DUPLICATE_NAME_TYPE = URI.create("urn:problem:duplicate-name");
    private static final URI NOT_FOUND_TYPE = URI.create("urn:problem:resource-not-found");
    private static final URI VALIDATION_TYPE = URI.create("urn:problem:validation-failed");

    @ExceptionHandler(DuplicateNameException.class)
    ProblemDetail handleDuplicateName(DuplicateNameException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problem.setType(DUPLICATE_NAME_TYPE);
        problem.setTitle("Duplicate name");
        problem.setProperty("errorCode", "DUPLICATE_NAME");
        return problem;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleNotFound(EntityNotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setType(NOT_FOUND_TYPE);
        problem.setTitle("Resource not found");
        problem.setProperty("errorCode", "RESOURCE_NOT_FOUND");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "One or more request fields are invalid.");
        problem.setType(VALIDATION_TYPE);
        problem.setTitle("Validation failed");
        problem.setProperty("errorCode", "VALIDATION_FAILED");
        problem.setProperty("errors", errors);
        return problem;
    }
}
