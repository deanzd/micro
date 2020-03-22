package com.eking.momp.model.to;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class EntityIdTO implements Serializable {

    private static final long serialVersionUID = -2271143388249687043L;

    private String modelCode;

    private String id;

}
