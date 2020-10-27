package CustomException;

public class CollectionException extends CustomException{
    public CollectionException() {}
    public CollectionException(String errorMessage){
        super(errorMessage);
    }
}
