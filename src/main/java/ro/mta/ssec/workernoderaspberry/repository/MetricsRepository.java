package ro.mta.ssec.workernoderaspberry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.mta.ssec.workernoderaspberry.model.Metrics;

import java.util.List;

public interface MetricsRepository extends JpaRepository<Metrics, Integer> {
    @Query("select m" +
            " from Metrics m" +
            " where m.metricType = :metricType" +
            " and m.isSynchronized=false")
    List<Metrics> findAllNonSynchronizedMetricsByType(@Param("metricType") String metricType);

    @Query("select distinct m.metricType from Metrics m")
    List<String> findAllTypes();
}
