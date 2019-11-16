package de.felten.mqtt.MqttMate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoCustomConversionsConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        // Spring Datas org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter always converts using
        // the current system timezone. As we are dealing with UTC timestamps everywhere, we need to overwrite this behaviour.
        // Original idea from http://lifeinide.com/post/2018-05-15-spring-data-mongo-date-without-timezone/
        List<Converter> converters = new ArrayList<>();
        converters.add(
                new Converter<LocalDateTime, Date>() {
                    @Override
                    public Date convert(@NonNull LocalDateTime source) {
                        return Date.from(source.atZone(ZoneOffset.UTC).toInstant());
                    }
                });
        converters.add(
                new Converter<Date, LocalDateTime>() {
                    @Override
                    public LocalDateTime convert(@NonNull Date source) {
                        return LocalDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC);
                    }
                }
        );
        return new MongoCustomConversions(converters);
    }
}
