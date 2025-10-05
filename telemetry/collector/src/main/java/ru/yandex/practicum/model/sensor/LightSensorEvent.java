package ru.yandex.practicum.model.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LightSensorEvent extends SensorEvent {
    /**
     * Качество сигнала связи
     */
    int linkQuality;

    /**
     * Уровень освещённости
     */
    int luminosity;

    @Override
    public @NonNull SensorEventType getEventType() {
        return SensorEventType.LIGHT_SENSOR;
    }
}
