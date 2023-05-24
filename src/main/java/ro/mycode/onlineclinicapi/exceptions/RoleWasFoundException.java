package ro.mycode.onlineclinicapi.exceptions;

public class RoleWasFoundException extends RuntimeException{

    public RoleWasFoundException() {
        super("Role was found!");
    }
}
