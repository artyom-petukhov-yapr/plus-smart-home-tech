package ru.yandex.practicum.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventProducer implements AutoCloseable {
    private final KafkaProducer<String, SpecificRecordBase> producer;

    @Override
    public void close() {
        producer.flush();
        producer.close();
    }

    public void send(ProducerRecord<String, SpecificRecordBase> record) {
        if (record == null) {
            log.warn("Record is null");
            return;
        }

        log.info("Sending event: {}", record);
        producer.send(record);
    }
}
