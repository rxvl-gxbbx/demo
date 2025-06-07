package com.example.demo.diff;


import com.example.demo.diff.tracker.ObjectDiffTracker;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.MyDiffable;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDiffCollector extends ObjectDiffTracker<CustomerDto> {

    private CustomerDiffCollector() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(CustomerDto.class);
    }

    public static CustomerDiffCollector getInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new CustomerDiffCollector();
    }

    @Override
    public Set<String> getFieldPaths(MyDiffable current, MyDiffable previous) {
        return this.collectFieldNamesById(collectDiffs((CustomerDto) current, (CustomerDto) previous)).values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
