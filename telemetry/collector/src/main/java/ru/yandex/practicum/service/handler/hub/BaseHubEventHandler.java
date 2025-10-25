package ru.yandex.practicum.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;
import ru.yandex.practicum.service.producer.KafkaEventProducer;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
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
    public abstract HubEventType getEventType();

    /**
     * Преобразовать HubEvent в Avro-событие.
     */
    protected abstract T toAvro(HubEvent hubEvent);

    @Override
    public void handle(HubEvent event) {
        if (event.getEventType() != getEventType()) {
            throw new IllegalArgumentException("Illegal event type: %s".formatted(event.getEventType()));
        }

        T payload = toAvro(event);

        HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, hubEventAvro);
        kafkaEventProducer.send(record);
    }
}
