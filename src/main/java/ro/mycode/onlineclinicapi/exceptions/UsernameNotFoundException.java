package ro.mycode.onlineclinicapi.exceptions;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException() {
        super("Username not found!");
    }
}
