package com.eking.momp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntityDTO {
    private String id;
    private ModelDTO model;
    private List<ModelFiledValueDTO> fields;
}
