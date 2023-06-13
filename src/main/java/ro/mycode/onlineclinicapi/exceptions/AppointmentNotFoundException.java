package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException() {
        super("Appointment not found!");
    }
}
