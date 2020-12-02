package Model.AbstractDataTypes;

import CustomException.CollectionException;

import java.util.Map;

public interface DictionaryInterface<K, V> {
    void add(K key, V value) throws CollectionException;
    V update(K key, V value) throws CollectionException;
    V lookup(K key) throws CollectionException;
    boolean isDefined(K key);
    String KeysToString();
    V remove(K key);
    Map<K, V> getContent();
}
