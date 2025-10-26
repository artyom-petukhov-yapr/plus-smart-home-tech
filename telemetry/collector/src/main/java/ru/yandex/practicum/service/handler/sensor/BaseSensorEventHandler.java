package ru.yandex.practicum.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.service.producer.KafkaEventProducer;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    final KafkaEventProducer kafkaEventProducer;

    /**
     * Топик, в который отправляются события.
     */
    @Value("${kafka.topics.sensors}")
    private String topic;

    /**
     * Тип события, которое обрабатывает данный обработчик.
     */
    @Override
    public abstract SensorEventProto.PayloadCase getEventType();

    /**
     * Преобразовать SensorEvent в Avro-событие.
     */
    protected abstract T toAvro(SensorEventProto sensorEvent);

    @Override
    public void handle(SensorEventProto event) {
        if (!event.getPayloadCase().equals(getEventType())) {
            throw new IllegalArgumentException("Illegal event type: %s".formatted(event.getPayloadCase()));
        }

        T payload = toAvro(event);

        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, sensorEventAvro);
        kafkaEventProducer.send(record);
    }
}
