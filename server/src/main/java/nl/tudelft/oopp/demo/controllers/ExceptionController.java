package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.exceptions.RedundantentityException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is contains global exception handlers.
 */
@ControllerAdvice
public class ExceptionController {
    /**
     * This ExceptionHandler returns a badRequest ResponseEntity,
     * when the asked entity does not exist.
     * @return a badRequest ResponseEntity
     */
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity invalidEntityName() {
        return ResponseEntity.badRequest().body("invalid Entity required");
    }

    /**
     * This ExceptionHandler returns a badRequest ResponseEntity,
     * when trying to retrieve non-existing entities(e.g. invalid foreign key),
     * when trying to add redundant entities.
     * @param e the thrown exception
     * @return a badRequest ResponseEntity
     */
    @ExceptionHandler(value = {JpaObjectRetrievalFailureException.class,
            RedundantentityException.class})
    public ResponseEntity entityNotFound(JpaObjectRetrievalFailureException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
