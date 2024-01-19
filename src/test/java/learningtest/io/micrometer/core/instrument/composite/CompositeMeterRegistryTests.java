package learningtest.io.micrometer.core.instrument.composite;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Disabled;
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

    @Disabled("See https://github.com/micrometer-metrics/micrometer/issues/3020")
    @Test
    void filtersOnIntermediateCompositeMeterRegistryDoNotSeemToWork() {
        CompositeMeterRegistry parentMeterRegistry = new CompositeMeterRegistry();

        CompositeMeterRegistry intermediateMeterRegistry = new CompositeMeterRegistry();
        intermediateMeterRegistry.config().meterFilter(MeterFilter.denyNameStartsWith("deny"));
        parentMeterRegistry.add(intermediateMeterRegistry);

        SimpleMeterRegistry leafMeterRegistry = new SimpleMeterRegistry();
        intermediateMeterRegistry.add(leafMeterRegistry);

        parentMeterRegistry.counter("deny.item");

        assertThat(parentMeterRegistry.getMeters()).hasSize(1);
        // This seems to be empty with and without the filter.
        assertThat(intermediateMeterRegistry.getMeters()).isEmpty();
        // Filters on intermediate composite meter registries don't seem to work.
        assertThat(leafMeterRegistry.getMeters()).isEmpty();
    }

}
