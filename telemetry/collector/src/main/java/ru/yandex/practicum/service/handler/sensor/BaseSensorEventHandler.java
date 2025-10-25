package ru.yandex.practicum.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.service.producer.KafkaEventProducer;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
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
    public abstract SensorEventType getEventType();

    /**
     * Преобразовать SensorEvent в Avro-событие.
     */
    protected abstract T toAvro(SensorEvent sensorEvent);

    @Override
    public void handle(SensorEvent event) {
        if (event.getEventType() != getEventType()) {
            throw new IllegalArgumentException("Illegal event type: %s".formatted(event.getEventType()));
        }

        T payload = toAvro(event);

        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, sensorEventAvro);
        kafkaEventProducer.send(record);
    }
}
