package com.eking.momp.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eking.momp.model.po.DimensionModelPO;
import com.eking.momp.model.po.DimensionPO;
import com.eking.momp.mybatis.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
public class DimensionModelDTO {

    private Integer id;

    private Integer dimensionId;

    private Integer modelId;

    private Integer showOrder;

    public DimensionModelDTO(DimensionModelPO dimensionModel) {
        this.id = dimensionModel.getId();
        this.dimensionId = dimensionModel.getDimensionId();
        this.modelId = dimensionModel.getModelId();
        this.showOrder = dimensionModel.getShowOrder();
    }

}
