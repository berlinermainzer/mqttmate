package de.felten.mqtt.MqttMate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

// {"device":"afe17/balcony","temperatures":[25.0,25.2],"id":"3bfb475f-40dd-4136-9ecc-d14727cdda78","timestampUtc":1573829261}
@Document
public class Temperature {

    @Id
    private String id;

    private LocalDateTime timestampUtc;

    private String device;

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

    @JsonSetter("timestampUtc")
    public void parseTimestampUtc(long epoch) {
        LocalDateTime timestampUtc = LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
        this.timestampUtc = timestampUtc;
    }

    @JsonGetter("timestampUtc")
    public long getTimestampUtcEpoch() {
        return timestampUtc.toEpochSecond(ZoneOffset.UTC);
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {
        this.temperatures = temperatures;
    }

    public void addTemperature(double temperature) {
        if (temperatures == null) {
            temperatures = new ArrayList<>();
        }

        temperatures.add(temperature);
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
                .append(device, that.device)
                .append(temperatures, that.temperatures)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(timestampUtc)
                .append(device)
                .append(temperatures)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("timestampUtc", timestampUtc)
                .append("device", device)
                .append("temperatures", temperatures)
                .toString();
    }

    public static Temperature from(String id, LocalDateTime timestampUtc, String device, Double... temperatures) {
        Temperature temperature = new Temperature();
        temperature.setId(id);
        temperature.setTimestampUtc(timestampUtc);
        temperature.setDevice(device);
        for (Double temp : temperatures) {
            temperature.addTemperature(temp);
        }
        return temperature;
    }

}
