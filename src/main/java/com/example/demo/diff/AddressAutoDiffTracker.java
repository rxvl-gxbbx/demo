package com.example.demo.diff;

import com.example.demo.diff.tracker.CollectionDiffTracker;
import com.example.demo.dto.AddressAutoLineDto;
import com.example.demo.dto.AddressAutoRecordDto;
import com.example.demo.dto.Identifiable;
import com.example.demo.dto.MyDiffable;
import org.apache.commons.lang3.builder.Diff;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressAutoDiffTracker extends CollectionDiffTracker<AddressAutoLineDto> {

    private AddressAutoDiffTracker() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(AddressAutoLineDto.class);
    }

    public static AddressAutoDiffTracker getInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new AddressAutoDiffTracker();
    }

    @Override
    public Set<String> getFieldPaths(MyDiffable current, MyDiffable previous) {
        AddressAutoRecordDto currentWrapper = (AddressAutoRecordDto) current;
        AddressAutoRecordDto previousWrapper = (AddressAutoRecordDto) previous;

        if (currentWrapper == null) currentWrapper = new AddressAutoRecordDto();
        if (previousWrapper == null) previousWrapper = new AddressAutoRecordDto();

        List<AddressAutoLineDto> currentBuildingAddresses = currentWrapper.getBuildingAddresses();
        List<AddressAutoLineDto> previousBuildingAddresses = previousWrapper.getBuildingAddresses();

        List<AddressAutoLineDto> currentLandAddresses = currentWrapper.getLandAddresses();
        List<AddressAutoLineDto> previousLandAddresses = previousWrapper.getLandAddresses();

        Set<String> buildingFieldNames = this.collectFieldNamesById(collectDiffs(currentBuildingAddresses, previousBuildingAddresses)).entrySet().stream()
                .flatMap(it -> {
                    String key = (String) it.getKey();
                    Set<String> value = it.getValue();

                    Identifiable dto = currentBuildingAddresses.stream()
                            .filter(address -> Objects.equals(key, address.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    String.format("Internal service error: object with key '%s' was not found. This should not happen under normal circumstances.", key)
                            ));

                    return value.stream()
                            .map(fieldName -> String.format("%s.%s.%s", fieldName, "b", currentBuildingAddresses.indexOf(dto)))
                            .collect(Collectors.toSet()).stream();
                })
                .collect(Collectors.toSet());

        Map<Object, List<Diff<?>>> diffs = new HashMap<>(this.getDiffs());
        Map<Object, Set<String>> modifiedFieldNames = new HashMap<>(this.getModifiedFieldNames());

        this.getDiffs().clear();
        this.getModifiedFieldNames().clear();

        Set<String> landFieldNames = this.collectFieldNamesById(collectDiffs(currentLandAddresses, previousLandAddresses)).entrySet().stream()
                .flatMap(it -> {
                    String key = (String) it.getKey();
                    Set<String> value = it.getValue();

                    Identifiable dto = currentLandAddresses.stream()
                            .filter(address -> Objects.equals(key, address.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    String.format("Internal service error: object with key '%s' was not found. This should not happen under normal circumstances.", key)
                            ));

                    return value.stream()
                            .map(fieldName -> String.format("%s.%s.%s", fieldName, "l", currentLandAddresses.indexOf(dto)))
                            .collect(Collectors.toSet()).stream();
                })
                .collect(Collectors.toSet());

        this.getDiffs().putAll(diffs);
        this.getModifiedFieldNames().putAll(modifiedFieldNames);

        return Stream.of(buildingFieldNames, landFieldNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
