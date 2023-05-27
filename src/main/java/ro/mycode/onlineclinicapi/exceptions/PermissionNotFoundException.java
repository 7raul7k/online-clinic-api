package ro.mycode.onlineclinicapi.exceptions;

public class PermissionNotFoundException extends RuntimeException{

    public PermissionNotFoundException() {
        super("Permission not found!");
    }
}
