package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException() {
        super("Username not found!");
    }
}
