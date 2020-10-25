package Model.AbstractDataTypes;

import CustomException.CustomException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements DictionaryInterface<K,V>{
    Map<K, V> map;
    //default constructor, initializez map as a HashMap
    public MyDictionary() { map = new HashMap<>();
    }

    @Override
    public void add(K key, V value) throws CustomException {
        if(map.containsKey(key))
            throw new CustomException("Element already exists!");
        map.put(key, value);
    }

    @Override
    public V update(K key, V value) throws CustomException {
        if(!map.containsKey(key))
            throw new CustomException("Key to which we want to update value doesn't exist!");
        return map.put(key, value);
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder dictionaryToString = new StringBuilder();
        for(K key: map.keySet()) {
            if(key != null){
                dictionaryToString.append((key.toString()));
                dictionaryToString.append("->");
                dictionaryToString.append(map.get(key).toString());
                dictionaryToString.append('\n');
            }
        }
        return dictionaryToString.toString();
        }
}
