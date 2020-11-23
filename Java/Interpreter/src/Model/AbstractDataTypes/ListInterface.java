package Model.AbstractDataTypes;

import CustomException.CollectionException;

public interface ListInterface<T> {
    void add(T element) throws CollectionException;
    T pop() throws CollectionException;
}
