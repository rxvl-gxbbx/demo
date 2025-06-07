package com.example.demo.diff;

import com.example.demo.diff.tracker.CollectionDiffTracker;
import com.example.demo.dto.AddressManualDto;
import com.example.demo.dto.AddressManualWrapper;
import com.example.demo.dto.Identifiable;
import com.example.demo.dto.MyDiffable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressManualDiffTracker extends CollectionDiffTracker<AddressManualDto> {

    private AddressManualDiffTracker() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(AddressManualDto.class);
    }

    public static AddressManualDiffTracker getInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new AddressManualDiffTracker();
    }

    @Override
    public Set<String> getFieldPaths(MyDiffable current, MyDiffable previous) {
        AddressManualWrapper currentAddress = (AddressManualWrapper) current;
        AddressManualWrapper previousAddress = (AddressManualWrapper) previous;

        if (currentAddress == null) currentAddress = new AddressManualWrapper();
        if (previousAddress == null) previousAddress = new AddressManualWrapper();

        List<AddressManualDto> currentAddresses = currentAddress.getAddresses();
        List<AddressManualDto> previousAddresses = previousAddress.getAddresses();

        return this.collectFieldNamesById(collectDiffs(currentAddresses, previousAddresses)).entrySet().stream()
                .flatMap(entry -> {
                    String key = (String) entry.getKey();
                    Set<String> value = entry.getValue();

                    Identifiable dto = currentAddresses.stream()
                            .filter(address -> Objects.equals(key, address.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    String.format("Internal service error: object with key '%s' was not found. This should not happen under normal circumstances.", key)
                            ));

                    return value.stream()
                            .map(fieldName -> String.format("%s.%s.%s", fieldName, "m", currentAddresses.indexOf(dto)))
                            .collect(Collectors.toSet()).stream();
                })
                .collect(Collectors.toSet());
    }
}
