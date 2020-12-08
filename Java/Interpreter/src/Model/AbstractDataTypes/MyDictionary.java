package Model.AbstractDataTypes;

import CustomException.CollectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDictionary<K, V> implements DictionaryInterface<K,V>{
    Map<K, V> map;
    //default constructor, initializes map as a HashMap
    public MyDictionary() { map = new HashMap<>();
    }

    @Override
    public void add(K key, V value) throws CollectionException {
        if(map.containsKey(key))
            throw new CollectionException("Element already exists!");
        map.put(key, value);
    }

    @Override
    public V update(K key, V value) throws CollectionException {
        if(!map.containsKey(key))
            throw new CollectionException("Key to which we want to update value doesn't exist!");
        return map.put(key, value);
    }

    @Override
    public V lookup(K key)throws CollectionException {
        V value = map.get(key);
        if(value == null)
            throw new CollectionException("variable " + key.toString() + " is not defined!\n");
        return value;
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public String KeysToString() {
        StringBuilder dictionaryToString = new StringBuilder();
        for(K key: map.keySet()) {
            if(key != null){
                dictionaryToString.append((key.toString()));
                dictionaryToString.append('\n');
            }
        }
        if(dictionaryToString.toString().isEmpty())
            return "EMPTY\n";
        return dictionaryToString.toString();
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public DictionaryInterface<K, V> deepCopy() {
        DictionaryInterface<K, V> copy = new MyDictionary<K, V>();
        map.keySet().forEach(e -> copy.add(e, map.get(e)));
        return copy;
    }

    @Override
    public List<V> getValues() {
        return new ArrayList<>(map.values());
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
        if(dictionaryToString.toString().isEmpty())
            return "EMPTY\n";
        return dictionaryToString.toString();
        }
}
