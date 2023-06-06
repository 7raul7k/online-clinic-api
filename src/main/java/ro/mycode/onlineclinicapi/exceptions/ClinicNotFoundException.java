package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClinicNotFoundException extends RuntimeException{

    public ClinicNotFoundException() {
        super("Clinic not found!");
    }
}
