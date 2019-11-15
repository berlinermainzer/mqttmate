package de.felten.mqtt.MqttMate.repository;

import de.felten.mqtt.MqttMate.model.Temperature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TemperatureRepositoryTest {

    @Autowired
    private TemperatureRepository temperatureRepository;

    private final String kitchen = "afe17/kitchen";
    private final String balcony = "afe17/balcony";

    @BeforeAll
    void setup() {

        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2019, 10, 1), LocalTime.NOON), kitchen, -2.0, -21.0));
        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2019, 10, 2), LocalTime.NOON), kitchen, 12.0, 12.0));

        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2018, 10, 1), LocalTime.NOON), balcony, 1.0, -2.0));
        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.NOON), balcony, 1.0, -2.0));
        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2019, 10, 1), LocalTime.NOON), balcony, 1.0, -2.0));
        temperatureRepository.save(Temperature.from(UUID.randomUUID().toString(),
                LocalDateTime.of(LocalDate.of(2021, 10, 1), LocalTime.NOON), balcony, 1.0, -2.0));
    }


    @Test
    void findByDeviceName() {
        List<Temperature> readingsByDeviceName = temperatureRepository.findByDeviceName(kitchen);
        assertThat(readingsByDeviceName).hasSize(2);
    }

    @Test
    void findByTimestampUtcBetween() {
        LocalDateTime gt = LocalDateTime.of(LocalDate.of(2019, 1, 1), LocalTime.NOON);
        LocalDateTime lt = LocalDateTime.of(LocalDate.of(2021, 10, 1), LocalTime.NOON.minusSeconds(1));
        List<Temperature> readingsByTimestampUtcBetween = temperatureRepository.findByTimestampUtcBetween(gt, lt);
        assertThat(readingsByTimestampUtcBetween).hasSize(4);
    }


    @Test
    void findByTimestampUtcBetweenAndByDeviceName() {
        LocalDateTime gt = LocalDateTime.of(LocalDate.of(2019, 1, 1), LocalTime.NOON);
        LocalDateTime lt = LocalDateTime.of(LocalDate.of(2021, 10, 1), LocalTime.NOON.minusSeconds(1));
        List<Temperature> readingsByTimestampUtcBetween = temperatureRepository.findByDeviceNameAndTimestampUtcBetween(balcony, gt, lt);
        assertThat(readingsByTimestampUtcBetween).hasSize(2);
    }
}