package ro.mta.ssec.workernoderaspberry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterNodeDTO {
    private String location;
    private String collector;
    private String metricType;
    private Double value;
}
