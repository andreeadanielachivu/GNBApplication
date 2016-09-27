package com.example.andreea.gnbapplication;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Andreea on 9/27/2016.
 */
public class UniqueQueue<T> implements Queue<T> {
    private final Queue<T> queue = new LinkedList<T>();
    private final Set<T> set = new HashSet<T>();

    public boolean add(T t) {
        // Only add element to queue if the set does not contain the specified element.
        if (set.add(t)) {
            queue.add(t);
        }

        return true; // Must always return true as per API def.
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean ret = false;
        for (T t: collection)
            if (set.add(t)) {
                queue.add(t);
                ret = true;
            }
        return ret;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return null;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    public T remove() throws NoSuchElementException {
        T ret = queue.remove();
        set.remove(ret);
        return ret;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    // TODO: Implement other Queue methods.
}
