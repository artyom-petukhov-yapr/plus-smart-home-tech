package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    String sensorId;

    /**
     * Тип условия
     */
    @NotNull
    ConditionType type;

    /**
     * Операция, применяемая к условию
     */
    @NotNull
    ConditionOperation operation;

    /**
     * Значение для сравнения, может быть null, int или boolean
     */
    Object value;
}
