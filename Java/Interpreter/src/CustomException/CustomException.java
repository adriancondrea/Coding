package CustomException;

public class CustomException extends Exception{
    public CustomException(){}
    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}