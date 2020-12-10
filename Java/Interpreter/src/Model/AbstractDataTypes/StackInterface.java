package Model.AbstractDataTypes;

import CustomException.CollectionException;

public interface StackInterface<T> {
    T pop() throws CollectionException;
    void push(T element);
    boolean isEmpty() throws CollectionException;
    T peek();
}
