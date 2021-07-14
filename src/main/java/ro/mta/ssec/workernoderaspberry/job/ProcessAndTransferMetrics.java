package ro.mta.ssec.workernoderaspberry.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.mta.ssec.workernoderaspberry.repository.MetricsRepository;
import ro.mta.ssec.workernoderaspberry.service.MasterNodeService;
import ro.mta.ssec.workernoderaspberry.service.MetricsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessAndTransferMetrics {
    private final MetricsRepository metricsRepository;
    private final MetricsService metricsService;
    private final MasterNodeService masterNodeService;

    @Value("${metrics.collector.name}")
    private String collector;
    @Value("${metrics.location.name}")
    private String location;

    @Scheduled(cron = "0 */2 * * * *")
    void processAndTransfer() {
        List<String> allTypes = metricsRepository.findAllTypes();
        Map<String, Double> typeAndValuesMap = new HashMap<>();

        allTypes.forEach(
                type -> typeAndValuesMap.put(type, metricsService.processAndTransferData(type))
        );

        typeAndValuesMap.forEach(
                (type, averageValue) -> {
                    if (!averageValue.isNaN()) {

                        log.info("Sending to master metrics of type={}, having value={}", type, averageValue);
                        try {
                            masterNodeService.writeMetricsToMasterNode(
                                    location,
                                    collector,
                                    type,
                                    averageValue
                            );
                        } catch (Exception e) {
                            log.error("Sending metrics to master failed. reason={}", e.getMessage());
                        }

                    }

                }
        );
    }
}
