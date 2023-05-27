package ro.mycode.onlineclinicapi.exceptions;

public class UsernameWasFoundException extends RuntimeException{

    public UsernameWasFoundException() {
        super("Username was found!");
    }
}
