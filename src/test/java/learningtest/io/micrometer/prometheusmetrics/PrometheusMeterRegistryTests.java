package learningtest.io.micrometer.prometheusmetrics;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PrometheusMeterRegistry}.
 *
 * @author Johnny Lim
 */
class PrometheusMeterRegistryTests {

    PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    @Test
    void distributionSummaryWithServiceLevelObjectives() {
        DistributionSummary summary = DistributionSummary.builder("my.distribution.summary")
                .serviceLevelObjectives(100, 200, 300)
                .register(this.registry);
        summary.record(50);
        summary.record(150);
        summary.record(250);
        summary.record(350);

        assertThat(this.registry.scrape())
                .contains("my_distribution_summary_bucket{le=\"100.0\"} 1")
                .contains("my_distribution_summary_bucket{le=\"200.0\"} 2")
                .contains("my_distribution_summary_bucket{le=\"300.0\"} 3")
                .contains("my_distribution_summary_bucket{le=\"+Inf\"} 4");
    }

}
