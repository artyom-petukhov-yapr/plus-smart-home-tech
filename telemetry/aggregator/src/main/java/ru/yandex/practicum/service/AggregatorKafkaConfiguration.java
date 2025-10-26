package ru.yandex.practicum.service;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.serializer.GeneralAvroSerializer;
import ru.yandex.practicum.serializer.SensorEventDeserializer;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class AggregatorKafkaConfiguration {
    private KafkaProducer<String, SensorsSnapshotAvro> producer;
    private KafkaConsumer<String, SensorEventAvro> consumer;

    @Value("${kafka.bootstrap-server}")
    private String bootStrapServer;

    @Value("${kafka.group-id}")
    private String groupId;

    @Bean
    public KafkaProducer<String, SensorsSnapshotAvro> getProducer() {
        if (producer == null) {
            initProducer();
        }
        return producer;
    }

    @Bean
    public KafkaConsumer<String, SensorEventAvro> getConsumer() {
        if (consumer == null) {
            initConsumer();
        }
        return consumer;
    }

    private void initConsumer() {
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        config.put(GROUP_ID_CONFIG, groupId);
        config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, SensorEventDeserializer.class.getName());
        consumer = new KafkaConsumer<>(config);
    }

    private void initProducer() {
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class.getName());
        producer = new KafkaProducer<>(config);
    }
}
