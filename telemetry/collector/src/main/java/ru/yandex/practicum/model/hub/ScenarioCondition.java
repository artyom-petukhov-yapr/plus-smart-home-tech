package ru.yandex.practicum.model.hub;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Условие активации сценария
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioCondition {
    /**
     * Идентификатор устройства
     */
    String sensorId;

    /**
     * Тип условия
     */
    ConditionType type;

    /**
     * Операция, применяемая к условию
     */
    ConditionOperation operation;

    /**
     * Значение для сравнения, может быть null, int или boolean
     */
    Object value;
}
