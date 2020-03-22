package com.eking.momp.model.dto;

import lombok.Data;

@Data
public class ModelFiledValueDTO {

    private String code;

    private String name;

    private String dataType;

    private Boolean showInList;

    private Object value;

}
