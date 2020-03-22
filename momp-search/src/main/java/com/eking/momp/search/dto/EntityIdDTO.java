package com.eking.momp.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class EntityIdDTO implements Serializable {

    private static final long serialVersionUID = -894228906471203964L;

    private String modelCode;

    private String id;

}
