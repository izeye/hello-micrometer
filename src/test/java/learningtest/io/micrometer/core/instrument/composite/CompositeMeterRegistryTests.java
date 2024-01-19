package learningtest.io.micrometer.core.instrument.composite;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CompositeMeterRegistry}.
 *
 * @author Johnny Lim
 */
class CompositeMeterRegistryTests {

    @Test
    void test() {
        CompositeMeterRegistry parentMeterRegistry = new CompositeMeterRegistry();
        SimpleMeterRegistry childMeterRegistry = new SimpleMeterRegistry();
        parentMeterRegistry.add(childMeterRegistry);

        String name = "my.counter";
        Counter counter = Counter.builder(name).register(parentMeterRegistry);
        counter.increment();

        assertThat(parentMeterRegistry.counter(name).count()).isOne();
        assertThat(childMeterRegistry.counter(name).count()).isOne();
    }

}
