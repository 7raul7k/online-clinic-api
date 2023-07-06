package ro.mycode.onlineclinicapi.exceptions;

public class AdministratorNotFoundException extends RuntimeException {

    public AdministratorNotFoundException() {

        super("Administrator not found!");
    }
}
