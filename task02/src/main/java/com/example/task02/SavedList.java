package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final List<E> inMemory = new ArrayList<E>();
    private final File file;

    public SavedList(File file) {
        this.file = file;
        deserialize();
    }

    @Override
    public E get(int index) {
        return inMemory.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = inMemory.set(index, element);
        serialize();
        return old;
    }

    @Override
    public int size() {
        return inMemory.size();
    }

    @Override
    public void add(int index, E element) {
        inMemory.add(index, element);
        serialize();
    }

    @Override
    public E remove(int index) {
        E old = inMemory.remove(index);
        serialize();
        return old;
    }

    private void deserialize() {
        if (!file.exists()) {
            return;
        }
        try (var ois = new ObjectInputStream(new FileInputStream(file))) {
            List<E> loaded = (List<E>) ois.readObject();
            inMemory.clear();
            inMemory.addAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot load list from" + file, e);
        }
    }

    private void serialize() {
        try (var oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(inMemory);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot save list to" + file, e);
        }
    }
}
