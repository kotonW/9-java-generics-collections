package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Pair<T, V> {
    private final T t;
    private final V v;

    private Pair(T t, V v) {
        this.t = t;
        this.v = v;
    }

    public static <T, V> Pair<T, V> of(T t, V v) {
        return new Pair<T, V>(t, v);
    }

    public T getFirst() {
        return this.t;
    }

    public V getSecond() {
        return this.v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(t, pair.t) && Objects.equals(v, pair.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, v);
    }

    public void ifPresent(BiConsumer<? super T, ? super V> consumer) {
        if (t != null && v != null) {
            consumer.accept(t, v);
        }
    }
}
