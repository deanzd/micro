package com.eking.momp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eking.momp.mybatis.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Dean
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dimension_model")
public class DimensionModelPO extends BasePO<DimensionModelPO> {

    private static final long serialVersionUID = 1L;

    private Integer dimensionId;

    private Integer modelId;

    private Integer showOrder;

}
