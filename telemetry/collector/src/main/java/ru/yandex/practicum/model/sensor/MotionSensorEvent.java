package ru.yandex.practicum.model.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotionSensorEvent extends SensorEvent{
    /**
     * Качество сигнала связи
     */
    @NotNull
    int linkQuality;

    /**
     * Обнаружено ли движение
     */
    @NotNull
    boolean motion;

    /**
     * Уровень напряжения
     */
    @NotNull
    int voltage;

    @Override
    public @NonNull SensorEventType getEventType() {
        return SensorEventType.MOTION_SENSOR;
    }
}