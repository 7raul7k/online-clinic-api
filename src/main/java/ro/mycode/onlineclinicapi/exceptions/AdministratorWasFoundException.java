package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AdministratorWasFoundException extends RuntimeException{

    public AdministratorWasFoundException() {

        super("Administrator was found !");
    }
}
