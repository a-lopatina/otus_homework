package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notify(key, value, Action.PUT);
    }

    @Override
    public void remove(K key) {
        V removedValue = cache.remove(key);
        notify(key, removedValue, Action.DELETE);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notify(key, value, Action.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(K key, V value, Action action) {
        for (HwListener<K, V> listener : listeners) {
            try {
                listener.notify(key, value, action.getActionType());
            } catch (Exception e) {
                throw new ListenerException("Что то пошло не так");
            }
        }
    }
}
