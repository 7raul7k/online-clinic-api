package ro.mycode.onlineclinicapi.exceptions;

public class ClinicNotFoundException extends RuntimeException{

    public ClinicNotFoundException() {
        super("Clinic not found!");
    }
}
