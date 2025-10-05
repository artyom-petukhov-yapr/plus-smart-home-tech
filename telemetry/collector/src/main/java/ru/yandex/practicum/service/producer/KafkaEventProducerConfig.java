/*package ru.yandex.practicum.service.producer;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.serializer.GeneralAvroSerializer;

import java.util.Properties;
import java.util.function.Supplier;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaEventProducerConfig {
    private final Supplier<KafkaProducer<String, SpecificRecordBase>> producer = this::createProducer;

    @Bean
    public KafkaProducer<String, SpecificRecordBase> getProducer() {
        return producer.get();
    }

    @Value("${kafka.bootstrap-server}")
    private String bootStrapServer;

    private synchronized KafkaProducer<String, SpecificRecordBase> createProducer() {
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class.getName());
        return new KafkaProducer<>(config);
    }
}
*/
package ru.yandex.practicum.service.producer;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.serializer.GeneralAvroSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaEventProducerConfig {
    private KafkaProducer<String, SpecificRecordBase> producer;

    @Value("${kafka.bootstrap-server}")
    private String bootStrapServer;

    @Bean
    public KafkaProducer<String, SpecificRecordBase> getProducer() {
        if (producer == null) {
            initProducer();
        }
        return producer;
    }

    private void initProducer() {
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class.getName());
        producer = new KafkaProducer<>(config);
    }
}