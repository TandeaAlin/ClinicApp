package application.exceptions;

public class InvalidDataException extends Exception{
    public InvalidDataException(String message){
        super(message);
    }

    public InvalidDataException() {
        this("Invalid data");
    }
}
