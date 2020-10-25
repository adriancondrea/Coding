package Model.AbstractDataTypes;

import CustomException.CustomException;

public interface ListInterface<T> {
    void add(T element) throws CustomException;
    T pop() throws CustomException;
}
