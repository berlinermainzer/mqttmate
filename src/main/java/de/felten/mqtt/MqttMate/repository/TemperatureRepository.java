package de.felten.mqtt.MqttMate.repository;

import de.felten.mqtt.MqttMate.model.Temperature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureRepository extends MongoRepository<Temperature, String>, PagingAndSortingRepository<Temperature, String> {

    List<Temperature> findByDevice(String device);

    Page<Temperature> findByDevice(String device, Pageable pageable);

    List<Temperature> findByTimestampUtcBetween(LocalDateTime timestampGT, LocalDateTime timestampLT);

    List<Temperature> findByDeviceAndTimestampUtcBetween(String device, LocalDateTime timestampGT, LocalDateTime timestampLT);
}
