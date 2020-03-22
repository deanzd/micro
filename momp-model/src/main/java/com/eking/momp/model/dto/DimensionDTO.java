package com.eking.momp.model.dto;

import com.eking.momp.model.po.DimensionPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DimensionDTO {

    private static final long serialVersionUID = 8552464827962661183L;

    private Integer id;

    private String name;

    private String description;

    private Integer modelId;

    private Integer showOrder;

    public DimensionDTO(DimensionPO dimension) {
        this.id = dimension.getId();
        this.name = dimension.getName();
        this.description = dimension.getDescription();
        this.modelId = dimension.getModelId();
        this.showOrder = dimension.getShowOrder();
    }
}
