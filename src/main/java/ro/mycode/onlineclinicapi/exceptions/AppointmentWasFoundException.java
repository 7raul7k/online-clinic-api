package ro.mycode.onlineclinicapi.exceptions;

public class AppointmentWasFoundException extends RuntimeException {

    public AppointmentWasFoundException() {
        super("Appointment was found!");
    }
}
