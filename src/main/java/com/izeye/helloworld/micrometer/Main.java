package com.izeye.helloworld.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Hello world for Micrometer.
 *
 * @author Johnny Lim
 */
public class Main {

    public static void main(String[] args) {
        SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();
        String name = "my.counter";
        Counter counter = Counter.builder(name).register(meterRegistry);
        counter.increment();

        Counter found = meterRegistry.counter(name);
        System.out.println(found.count());
    }

}
