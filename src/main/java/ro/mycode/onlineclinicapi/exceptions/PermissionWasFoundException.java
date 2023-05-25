package ro.mycode.onlineclinicapi.exceptions;

public class PermissionWasFoundException extends RuntimeException{

    public PermissionWasFoundException() {
        super("Permission was found!");
    }
}
