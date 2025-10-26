package ru.yandex.practicum.service.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

/**
 * Интерфейс обработчика событий сенсора
 */
public interface SensorEventHandler {
    /**
     * Получить тип события, которое обрабатывает данный обработчик
     */
    SensorEventProto.PayloadCase getEventType();

    /**
     * Обработать событие
     */
    void handle(SensorEventProto event);
}
