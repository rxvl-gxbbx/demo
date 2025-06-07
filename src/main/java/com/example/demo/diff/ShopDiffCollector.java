package com.example.demo.diff;

import com.example.demo.diff.tracker.CollectionDiffTracker;
import com.example.demo.dto.MyDiffable;
import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopDiffCollector extends CollectionDiffTracker<ShopDto> {

    private ShopDiffCollector() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(ShopDto.class);
    }

    public static ShopDiffCollector getInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ShopDiffCollector();
    }

    @Override
    public Set<String> getFieldPaths(MyDiffable current, MyDiffable previous) {
        ShopWrapper currentShopWrapper =
                Objects.requireNonNullElseGet((ShopWrapper) current, ShopWrapper::new);
        ShopWrapper previousShopWrapper =
                Objects.requireNonNullElseGet((ShopWrapper) previous, ShopWrapper::new);

        List<ShopDto> currentShops = currentShopWrapper.getShops();
        List<ShopDto> previousShops = previousShopWrapper.getShops();

        return this.collectFieldNamesById(collectDiffs(currentShops, previousShops)).values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
