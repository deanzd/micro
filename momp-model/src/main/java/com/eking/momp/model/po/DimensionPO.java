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
@TableName("dimension")
public class DimensionPO extends BasePO<DimensionPO> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private Integer modelId;

    private Integer showOrder;

}
