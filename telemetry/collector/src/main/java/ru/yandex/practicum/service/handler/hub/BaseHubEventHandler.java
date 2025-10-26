package ru.yandex.practicum.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.service.producer.KafkaEventProducer;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    final KafkaEventProducer kafkaEventProducer;

    /**
     * Топик, в который отправляются события.
     */
    @Value("${kafka.topics.hubs}")
    private String topic;

    /**
     * Тип события, которое обрабатывает данный обработчик.
     */
    @Override
    public abstract HubEventProto.PayloadCase getEventType();

    /**
     * Преобразовать HubEvent в Avro-событие.
     */
    protected abstract T toAvro(HubEventProto hubEvent);

    @Override
    public void handle(HubEventProto event) {
        if (!event.getPayloadCase().equals(getEventType())) {
            throw new IllegalArgumentException("Illegal event type: %s".formatted(event.getPayloadCase()));
        }

        T payload = toAvro(event);

        HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, hubEventAvro);
        kafkaEventProducer.send(record);
    }
}
