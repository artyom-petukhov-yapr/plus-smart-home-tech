package ru.yandex.practicum.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.deserializer.HubEventDeserializer;
import ru.yandex.practicum.deserializer.SensorsSnapshotDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class AnalyzerKafkaConfiguration {
    private KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer;
    private KafkaConsumer<String, HubEventAvro> hubConsumer;

    @Value("${kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Value("${kafka.group-id.snapshot}")
    private String snapshotsGroupId;

    @Value("${kafka.group-id.hub}")
    private String hubsGroupId;

    @Bean
    public KafkaConsumer<String, SensorsSnapshotAvro> getSnapshotConsumer() {
        if (snapshotConsumer == null) {
            Properties config = new Properties();
            config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
            config.put(GROUP_ID_CONFIG, snapshotsGroupId);
            config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            config.put(VALUE_DESERIALIZER_CLASS_CONFIG, SensorsSnapshotDeserializer.class.getName());
            config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            return new KafkaConsumer<>(config);
        }
        return snapshotConsumer;
    }

    @Bean
    public KafkaConsumer<String, HubEventAvro> getHubConsumer() {
        if (hubConsumer == null) {
            Properties config = new Properties();
            config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
            config.put(GROUP_ID_CONFIG, hubsGroupId);
            config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            config.put(VALUE_DESERIALIZER_CLASS_CONFIG, HubEventDeserializer.class.getName());
            config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            return new KafkaConsumer<>(config);
        }
        return hubConsumer;
    }
}
