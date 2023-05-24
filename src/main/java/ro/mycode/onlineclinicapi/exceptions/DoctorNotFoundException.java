package ro.mycode.onlineclinicapi.exceptions;

public class DoctorNotFoundException extends RuntimeException{

    public DoctorNotFoundException() {
        super("Doctor not found");
    }
}
