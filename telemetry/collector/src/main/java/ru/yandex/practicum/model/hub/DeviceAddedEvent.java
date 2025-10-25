package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAddedEvent extends HubEvent {
    /**
     * Уникальный идентификатор устройства
     */
    @NotBlank
    String id;

    /**
     * Тип устройства
     */
    @NonNull
    DeviceType deviceType;

    @Override
    public @NonNull HubEventType getEventType() {
        return HubEventType.DEVICE_ADDED;
    }
}
