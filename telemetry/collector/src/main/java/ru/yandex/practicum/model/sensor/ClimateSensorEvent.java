package ru.yandex.practicum.model.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClimateSensorEvent extends SensorEvent {
    /**
     * Температура в градусах Цельсия
     */
    int temperatureC;

    /**
     * Уровень влажности
     */
    int humidity;

    /**
     * Уровень углекислого газа
     */
    int co2Level;

    @Override
    public SensorEventType getEventType() {
        return SensorEventType.CLIMATE_SENSOR;
    }
}