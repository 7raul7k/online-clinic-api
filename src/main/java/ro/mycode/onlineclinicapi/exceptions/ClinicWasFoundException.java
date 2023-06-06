package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClinicWasFoundException extends RuntimeException{

    public ClinicWasFoundException() {
        super("Clinic was found !");
    }
}
