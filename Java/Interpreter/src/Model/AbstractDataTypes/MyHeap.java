package Model.AbstractDataTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<V> implements HeapInterface<V>{
    AtomicInteger heapIndex;
    Map<Integer, V> heap;

    public MyHeap() {
        heap = new HashMap<>();
        heapIndex = new AtomicInteger(0);
    }

    @Override
    public int allocate(V value) {
        heap.put(heapIndex.incrementAndGet(), value);
        return heapIndex.get();
    }

    @Override
    public V getValue(int address) {
        return heap.get(address);
    }

    @Override
    public void putValueAtAddress(int address, V value) {
        heap.put(address, value);
    }

    @Override
    public V deallocateAddress(int address) {
        return heap.remove(address);
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, V> heap) {
        this.heap = heap;
    }

    @Override
    public boolean allocatedAddress(int address) {
        return heap.containsKey(address);
    }

    @Override
    public String toString() {
        StringBuilder stringValue = new StringBuilder();
        for(Integer key: heap.keySet()) {
            if(key != null)
                stringValue.append(key.toString()).append(" -> ").append(heap.get(key)).append('\n');
        }
        return stringValue.toString();
    }
}
