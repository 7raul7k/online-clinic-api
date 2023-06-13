package ro.mycode.onlineclinicapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppointmentWasFoundException extends RuntimeException {

    public AppointmentWasFoundException() {
        super("Appointment was found!");
    }
}
