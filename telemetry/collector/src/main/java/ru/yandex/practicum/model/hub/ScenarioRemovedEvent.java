package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Событие удаления сценария
 */
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ScenarioRemovedEvent extends HubEvent {
    /**
     * Название сценария, уникальное в рамках хаба
     */
    String name;

    @Override
    public @NonNull HubEventType getEventType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
