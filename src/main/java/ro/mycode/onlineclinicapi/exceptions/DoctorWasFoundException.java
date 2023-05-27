package ro.mycode.onlineclinicapi.exceptions;

public class DoctorWasFoundException extends RuntimeException{

    public DoctorWasFoundException() {
        super("Doctor was found!");
    }
}
