package com.best2team.facebook_clone_be.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Exception {
    private String msg;
    private HttpStatus httpStatus;
}
