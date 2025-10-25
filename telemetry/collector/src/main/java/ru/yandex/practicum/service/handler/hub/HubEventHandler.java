package ru.yandex.practicum.service.handler.hub;

import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

/**
 * Интерфейс обработчика событий для хаба
 */
public interface HubEventHandler {
    /**
     * Получить тип события, которое обрабатывает данный обработчик
     */
    HubEventType getEventType();

    /**
     * Обработать событие
     */
    void handle(HubEvent event);
}
