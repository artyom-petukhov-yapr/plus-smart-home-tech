package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Событие добавления сценария
 */
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {
    /**
     * Уникальное название сценария
     */
    @NotBlank
    String name;

    /**
     * Набор условий активации сценария
     */
    @NotEmpty
    List<ScenarioCondition> conditions;

    /**
     * Набор действий, выполняемых при активации сценария
     */
    @NotEmpty
    List<DeviceAction> actions;

    @Override
    public @NonNull HubEventType getEventType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
