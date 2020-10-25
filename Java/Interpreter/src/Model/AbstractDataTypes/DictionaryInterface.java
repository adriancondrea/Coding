package Model.AbstractDataTypes;

import CustomException.CustomException;

public interface DictionaryInterface<K, V> {
    void add(K key, V value) throws CustomException;
    V update(K key, V value) throws CustomException;
    V lookup(K key);
    boolean isDefined(K key);
}
