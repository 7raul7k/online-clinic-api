package ro.mycode.onlineclinicapi.exceptions;

public class TestPatientWasFoundException extends RuntimeException{

    public TestPatientWasFoundException() {
        super("Test patient was found!");
    }
}
