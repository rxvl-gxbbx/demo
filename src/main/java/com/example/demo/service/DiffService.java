package com.example.demo.service;

import com.example.demo.diff.*;
import com.example.demo.dto.Form;
import com.example.demo.dto.MyDiffable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DiffService {

    // Как сделать так, чтобы я для всех типов form мог вызвать один метод getFieldPaths без множества case в switch expression?
    // То есть проблема в том, что если у меня появится еще один тип form, то мне придется добавлять еще один case
    private Set<String> getModifiedFieldNames(Form form, MyDiffable current, MyDiffable previous) throws Exception {
        return switch (form) {
            case ADDRESS_AUTO -> {
                yield AddressAutoDiffTracker.getInstance().getFieldPaths(current, previous);
            }
            case ADDRESS_MANUAL -> {
                yield AddressManualDiffTracker.getInstance().getFieldPaths(current, previous);
            }
            case CUSTOMER -> {
                yield CustomerDiffCollector.getInstance().getFieldPaths(current, previous);
            }
            case DOCUMENTS -> {
                yield DocumentsDiffCollector.getInstance().getFieldPaths(current, previous);
            }
            case SHOP -> {
                yield ShopDiffCollector.getInstance().getFieldPaths(current, previous);
            }
        };
    }
}
