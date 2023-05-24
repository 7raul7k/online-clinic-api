package ro.mycode.onlineclinicapi.exceptions;

public class ListEmptyException extends RuntimeException{

    public ListEmptyException() {
        super("List is empty!");
    }
}
