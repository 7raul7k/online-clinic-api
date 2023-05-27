package ro.mycode.onlineclinicapi.exceptions;

public class ClinicWasFoundException extends RuntimeException{

    public ClinicWasFoundException() {
        super("Clinic was found !");
    }
}
