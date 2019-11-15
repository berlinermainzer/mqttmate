package de.felten.mqtt.MqttMate.repository;

import de.felten.mqtt.MqttMate.model.Temperature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TemperatureRepositoryTest {

    @Autowired
    TemperatureRepository temperatureRepository;

    @BeforeAll
    public void setup() {
        Temperature temperature = new Temperature();
        temperature.setDeviceName("afe17/balcony");
        temperature.setId(UUID.randomUUID().toString());
        temperature.setTimestampUtc(LocalDateTime.now(ZoneOffset.UTC));
        List<Double> readings = new ArrayList<>();
        readings.add(21.0);
        readings.add(-2.5);
        temperature.setTemperatures(readings);

        temperatureRepository.save(temperature);

        Temperature temperature2 = new Temperature();
        temperature2.setDeviceName("afe17/balcony");
        temperature2.setId(UUID.randomUUID().toString());
        temperature2.setTimestampUtc(LocalDateTime.now(ZoneOffset.UTC));
        List<Double> readings2 = new ArrayList<>();
        readings2.add(-1.0);
        readings2.add(2.5);
        temperature2.setTemperatures(readings2);

        temperatureRepository.save(temperature2);

        Temperature temperature3 = new Temperature();
        temperature3.setDeviceName("afe17/kitchen");
        temperature3.setId(UUID.randomUUID().toString());
        temperature3.setTimestampUtc(LocalDateTime.now(ZoneOffset.UTC));
        List<Double> readings3 = new ArrayList<>();
        readings3.add(12.0);
        readings3.add(12.0);
        temperature3.setTemperatures(readings3);

        temperatureRepository.save(temperature3);
    }

    @Test
    public void findByDeviceName() {
        List<Temperature> readingsByDeviceName = temperatureRepository.findByDeviceName("afe17/balcony");
        assertThat(readingsByDeviceName).hasSize(2);
    }

   // @Test
    public void findByTimestampUtcBetween() {
    }
}