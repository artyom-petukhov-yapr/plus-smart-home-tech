package ru.yandex.practicum.service.handler.sensor;

import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;

/**
 * Интерфейс обработчика событий сенсора
 */
public interface SensorEventHandler {
    /**
     * Получить тип события, которое обрабатывает данный обработчик
     */
    SensorEventType getEventType();

    /**
     * Обработать событие
     */
    void handle(SensorEvent event);
}
