package com.eking.momp.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityRelationDetailDTO implements Serializable {

    private static final long serialVersionUID = -8327763570975871722L;
    private String id;

    private ModelRelationDetailDTO modelRelation;

    private Map<String, Object> entity;

    private Map<String, Object> targetEntity;

    private List<ModelFiledValueDTO> showInListFields;
}
