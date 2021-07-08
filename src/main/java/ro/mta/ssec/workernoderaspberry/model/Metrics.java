package ro.mta.ssec.workernoderaspberry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "collected_at", nullable = false)
    private String collectedAt;

    private String location;
    private String collector;
    private String metricType;
    private boolean isSynchronized;
    private float value;

    @PrePersist
    protected void onCreate() throws ParseException {
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        collectedAt = sdf.format(date1);
    }

}
