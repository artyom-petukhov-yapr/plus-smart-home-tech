package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAction {
    /**
     * Идентификатор устройства
     */
    @NotBlank
    String sensorId;

    /**
     * Тип действия
     */
    @NotNull
    ActionType type;

    /**
     * Значение, устанавливаемое при выполнении действия (если применимо)
     */
    Integer value;
}
