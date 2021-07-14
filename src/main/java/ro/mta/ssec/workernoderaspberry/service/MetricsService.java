package ro.mta.ssec.workernoderaspberry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.mta.ssec.workernoderaspberry.model.Metrics;
import ro.mta.ssec.workernoderaspberry.repository.MetricsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final MetricsRepository metricsRepository;

    @Transactional
    public Double processAndTransferData(String type) {
        List<Metrics> allNonSynchronizedMetrics = metricsRepository.findAllNonSynchronizedMetricsByType(type);
        Double averagePerType = allNonSynchronizedMetrics
                .stream()
                .mapToDouble(Metrics::getValue)
                .average()
                .orElse(Double.NaN);

        allNonSynchronizedMetrics.forEach(metricsEntry -> metricsEntry.setSynchronized(true));
        metricsRepository.saveAll(allNonSynchronizedMetrics);

        Double averagePerTypeTruncated = Math.floor(averagePerType * 100) / 100;
        return averagePerTypeTruncated;
    }
}
