package ro.mycode.onlineclinicapi.exceptions;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException() {
        super("Patient not found!");
    }
}
