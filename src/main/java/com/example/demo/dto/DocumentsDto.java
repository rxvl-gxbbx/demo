package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/*
Ключ имеет вид doc_0001, doc_0002 и т.д.
 */
@Getter
@Setter
public class DocumentsDto extends LinkedHashMap<String, DocumentDto> implements MyDiffable {

}
