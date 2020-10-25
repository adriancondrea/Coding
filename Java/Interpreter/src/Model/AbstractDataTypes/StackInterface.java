package Model.AbstractDataTypes;

import CustomException.CustomException;

public interface StackInterface<T> {
    T pop() throws CustomException;
    void push(T element) throws CustomException;
    boolean isEmpty() throws CustomException;
}
