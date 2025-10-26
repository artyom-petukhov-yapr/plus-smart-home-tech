package ru.yandex.practicum.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.service.producer.KafkaEventProducer;

@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    public ScenarioAddedEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public ScenarioAddedEventAvro toAvro(HubEventProto hubEvent) {
        ScenarioAddedEventProto event = hubEvent.getScenarioAdded();
        return ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setActions(event.getActionList().stream().map(this::toAvro).toList())
                .setConditions(event.getConditionList().stream().map(this::toAvro).toList())
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getEventType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    private DeviceActionAvro toAvro(DeviceActionProto deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                .build();
    }

    private ScenarioConditionAvro toAvro(ScenarioConditionProto scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setValue(
                        switch (scenarioCondition.getValueCase()) {
                            case INT_VALUE -> scenarioCondition.getIntValue();
                            case BOOL_VALUE -> scenarioCondition.getBoolValue();
                            case VALUE_NOT_SET -> null;
                        }
                )
                .setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                .build();
    }
}