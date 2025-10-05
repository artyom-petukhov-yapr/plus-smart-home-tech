package ru.yandex.practicum.model.sensor;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemperatureSensorEvent extends SensorEvent {
    /**
     * Температура в градусах Цельсия.
     */
    int temperatureC;

    /**
     * Температура в градусах Фаренгейта.
     */
    int temperatureF;

    @Override
    public @NonNull SensorEventType getEventType() {
        return SensorEventType.TEMPERATURE_SENSOR;
    }
}