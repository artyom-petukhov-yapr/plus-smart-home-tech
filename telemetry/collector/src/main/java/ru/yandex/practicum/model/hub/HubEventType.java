package ru.yandex.practicum.model.hub;

/**
 * Типы событий хаба
 */
public enum HubEventType {
    /**
     * Событие добавления устройства
     */
    DEVICE_ADDED,

    /**
     * Событие удаления устройства
     */
    DEVICE_REMOVED,

    /**
     * Событие добавления сценария
     */
    SCENARIO_ADDED,

    /**
     * Событие удаления сценария
     */
    SCENARIO_REMOVED
}
