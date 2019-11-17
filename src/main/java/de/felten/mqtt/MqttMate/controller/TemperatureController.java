package de.felten.mqtt.MqttMate.controller;

import de.felten.mqtt.MqttMate.model.Temperature;
import de.felten.mqtt.MqttMate.repository.TemperatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequestMapping("temperature")
public class TemperatureController {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureController.class);

    @Autowired
    private TemperatureRepository temperatureRepository;

    @PostConstruct
    public void init() {
        logger.info("Starting TemperatureController.");
    }

    @GetMapping("/latest")
    public ResponseEntity<Temperature> getLatestTemperature() {
        Page<Temperature> temperatures = temperatureRepository.findAll(PageRequest.of(0, 1, Sort.Direction.DESC, "timestampUtc"));
        Optional<Temperature> latestTemp = temperatures.get().findAny();
        if (latestTemp.isPresent()) {
            return ResponseEntity.ok(latestTemp.get());
        }
        return ResponseEntity.notFound().build();
    }

}
