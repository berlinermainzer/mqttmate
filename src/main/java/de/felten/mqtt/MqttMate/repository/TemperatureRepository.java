package de.felten.mqtt.MqttMate.repository;

import de.felten.mqtt.MqttMate.model.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureRepository extends MongoRepository<Temperature, String>, PagingAndSortingRepository<Temperature, String> {

    List<Temperature> findByDeviceName(String deviceName);

    List<Temperature> findByTimestampUtcBetween(LocalDateTime timestampGT, LocalDateTime timestampLT);

    List<Temperature> findByDeviceNameAndTimestampUtcBetween(String deviceName, LocalDateTime timestampGT, LocalDateTime timestampLT);
}
