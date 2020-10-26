package Model.AbstractDataTypes;

import CustomException.CustomException;

public interface StackInterface<T> {
    T pop() throws CustomException;
    void push(T element);
    boolean isEmpty() throws CustomException;
}
