package ro.mycode.onlineclinicapi.exceptions;

public class PatientWasFoundException extends RuntimeException{

    public PatientWasFoundException() {
        super("Patient was found!");
    }
}
