package ro.mta.ssec.workernoderaspberry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.mta.ssec.workernoderaspberry.dto.MasterNodeDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterNodeService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${metrics.masterNode.ip}")
    private String masterNodeIp;

    public void writeMetricsToMasterNode(
            String location,
            String collector,
            String metricType,
            Double value
    ) {
        MasterNodeDTO requestDto = MasterNodeDTO.builder()
                .location(location)
                .collector(collector)
                .metricType(metricType)
                .value(value)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(
                "http://" + masterNodeIp + ":8080/api/metrics",
                HttpMethod.POST,
                new HttpEntity<>(requestDto),
                String.class
        );


        log.info("Master Node response = {}", response);
    }
}
