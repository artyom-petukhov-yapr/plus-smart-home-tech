package ru.yandex.practicum.service.handler.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

/**
 * Интерфейс обработчика событий для хаба
 */
public interface HubEventHandler {
    /**
     * Получить тип события, которое обрабатывает данный обработчик
     */
    HubEventProto.PayloadCase getEventType();

    /**
     * Обработать событие
     */
    void handle(HubEventProto event);
}
