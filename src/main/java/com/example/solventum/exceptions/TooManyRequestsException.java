package com.example.solventum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// If the requirements specified a need for verbose responses, I would modify the error controller and add a constructor here that would allow for more context
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class TooManyRequestsException extends RuntimeException {}
