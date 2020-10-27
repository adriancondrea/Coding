package CustomException;

public class StatementException extends CustomException{
    public StatementException() {}
    public StatementException(String errorMessage){
        super(errorMessage);
    }
}
