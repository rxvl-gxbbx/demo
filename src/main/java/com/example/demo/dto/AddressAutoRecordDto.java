package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressAutoRecordDto {

    private List<AddressAutoLineDto> buildingAddresses;

    private List<AddressAutoLineDto> landAddresses;

}
