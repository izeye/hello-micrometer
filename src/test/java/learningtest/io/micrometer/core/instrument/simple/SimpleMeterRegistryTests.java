package learningtest.io.micrometer.core.instrument.simple;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SimpleMeterRegistry}.
 *
 * @author Johnny Lim
 */
class SimpleMeterRegistryTests {

    private final SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();

    @Test
    void test() {
        String name = "my.counter";
        Counter counter = Counter.builder(name).register(this.meterRegistry);
        counter.increment();

        Counter found = this.meterRegistry.counter(name);
        assertThat(found.count()).isOne();
    }

}
