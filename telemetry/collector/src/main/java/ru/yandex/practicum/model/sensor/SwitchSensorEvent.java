package ru.yandex.practicum.model.sensor;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwitchSensorEvent extends SensorEvent {
    /**
     * Текущее состояние переключателя (включено/выключено)
     */
    boolean state;

    @Override
    public @NonNull SensorEventType getEventType() {
        return SensorEventType.SWITCH_SENSOR;
    }
}