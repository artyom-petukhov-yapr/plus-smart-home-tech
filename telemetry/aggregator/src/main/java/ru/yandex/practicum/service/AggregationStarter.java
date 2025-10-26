package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

    @Value("${kafka.topics.out}")
    private String outTopic;

    @Value("${kafka.topics.in}")
    private String inTopic;

    private final KafkaProducer<String, SensorsSnapshotAvro> producer;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final SnapshotProcessor snapshotProcessor;

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(inTopic));

            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    snapshotProcessor.updateState(record.value())
                            .ifPresent(snapshotAvro -> producer.send(new ProducerRecord<>(outTopic, snapshotAvro)));
                }
                consumer.commitSync();
            }
        } catch (WakeupException ignored) {
        } catch (Exception e) {
            log.error("Error while processing events from sensors", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();

            } finally {
                log.info("Closing consumer");
                consumer.close();
                log.info("Closing producer");
                producer.close();
            }
        }
    }
}
