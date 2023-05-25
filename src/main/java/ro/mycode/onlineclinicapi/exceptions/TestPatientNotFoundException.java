package ro.mycode.onlineclinicapi.exceptions;

public class TestPatientNotFoundException extends RuntimeException{

    public TestPatientNotFoundException() {
        super("Test patient not found!");
    }
}
