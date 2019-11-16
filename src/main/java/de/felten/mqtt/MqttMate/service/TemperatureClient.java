package de.felten.mqtt.MqttMate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.felten.mqtt.MqttMate.model.Temperature;
import de.felten.mqtt.MqttMate.repository.TemperatureRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TemperatureClient implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureClient.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Value("${mqtt.server.uri}")
    private String serverURI;

    @Value("${mqtt.server.topic}")
    private String topic;

    @Value("${mqtt.client.id}")
    private String clientId;

    private IMqttClient mqttClient;

    @PostConstruct
    public void init() throws MqttException {
        logger.info("Connecting to {} on topic {} as {}.", serverURI, topic, clientId);
        mqttClient = new MqttClient(serverURI, clientId);

        // connect wtih options
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        // TODO: sinnvolle opts, z.B. Username/Password?
        mqttClient.connect(options);
        logger.info("Connected.");

        // setCallBack
        mqttClient.setCallback(this);

        // subscribe
        mqttClient.subscribe(topic);
        logger.info("Subscribed.");
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.warn("Connection lost: {}", throwable.getMessage());
    }

    @Override
    public void messageArrived(String topicName, MqttMessage mqttMessage) throws Exception {
        logger.info("Message {} from {} arrived.", mqttMessage.getId(), topicName);

        byte[] payload = mqttMessage.getPayload();
        if (ArrayUtils.isEmpty(payload)) {
            logger.warn("Message {} has no payload.", mqttMessage.getId());
            return;
        }

        try {
            Temperature temperature = objectMapper.readValue(payload, Temperature.class);
            temperatureRepository.save(temperature);
            logger.info("Message {} saved.", mqttMessage.getId());
        } catch (Exception e) {
            logger.error("Failed to process message " + mqttMessage.getId(), e);
            throw e;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
