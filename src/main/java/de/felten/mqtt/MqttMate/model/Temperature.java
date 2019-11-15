package de.felten.mqtt.MqttMate.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

// {"device":"afe17/balcony","temperatures":[25.0,25.2],"id":"3bfb475f-40dd-4136-9ecc-d14727cdda78","timestampUtc":1573829261}
@Document
public class Temperature {

    @Id
    private String id;

    private LocalDateTime timestampUtc;

    private String deviceName;

    List<Double> temperatures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestampUtc() {
        return timestampUtc;
    }

    public void setTimestampUtc(LocalDateTime timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Temperature that = (Temperature) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(timestampUtc, that.timestampUtc)
                .append(deviceName, that.deviceName)
                .append(temperatures, that.temperatures)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(timestampUtc)
                .append(deviceName)
                .append(temperatures)
                .toHashCode();
    }
}
