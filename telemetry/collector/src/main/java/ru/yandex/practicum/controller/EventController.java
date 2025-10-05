package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.service.handler.sensor.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {
    /**
     * Обработчики событий для хабов
     */
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    /**
     * Обработчики событий для датчиков
     */
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;

    public EventController(Set<HubEventHandler> hubEventHandlers, Set<SensorEventHandler> sensorEventHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getEventType, Function.identity()));
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getEventType, Function.identity()));
    }

    @PostMapping("/hubs")
    public void processHubEvent(@RequestBody @Valid HubEvent event) {
        log.info("Hub event: {}", event.toString());
        HubEventHandler handler = hubEventHandlers.get(event.getEventType());
        if (handler == null) {
            throw new IllegalArgumentException("Unknown hub event type: " + event.getEventType());
        }
        handler.handle(event);
    }

    @PostMapping("/sensors")
    public void processSensorEvent(@RequestBody @Valid SensorEvent event) {
        log.info("Sensor event: {}", event.toString());
        SensorEventHandler handler = sensorEventHandlers.get(event.getEventType());
        if (handler == null) {
            throw new IllegalArgumentException("Unknown sensor event type: " + event.getEventType());
        }
        handler.handle(event);
    }
}
