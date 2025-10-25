package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceRemovedEvent extends HubEvent {

    /**
     * Уникальный идентификатор устройства
     */
    @NotBlank
    String id;

    @Override
    public @NonNull HubEventType getEventType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
