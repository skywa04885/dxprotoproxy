package com.github.skywa04885.dxprotoproxy;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Producer<T> {
    private final List<WeakReference<IConsumer<T>>> consumers;

    public Producer() {
        consumers = Collections.synchronizedList(new LinkedList<>());
    }

    public void listen(WeakReference<IConsumer<T>> consumer) {
        consumers.add(consumer);
    }

    public void produce(T value) {
        consumers.removeIf(weakConsumer -> {
            final @Nullable IConsumer<T> consumer = weakConsumer.get();
            if (consumer == null) {
                return true;
            }

            consumer.consume(value);

            return false;
        });
    }
}
