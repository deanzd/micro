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
@TableName("model_field")
public class ModelFieldPO extends BasePO<ModelFieldPO> {

    private static final long serialVersionUID = 1L;

    private String code;

    private Integer modelId;

    private String name;

    private String description;

    private Boolean required;

    private String dataType;

    private String verifyRegex;

    private Boolean showInTable;

    private Boolean showInList;

    private Boolean searchKey;

    private Integer showOrder;

}
