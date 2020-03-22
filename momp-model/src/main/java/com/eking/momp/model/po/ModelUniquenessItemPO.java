package com.eking.momp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("model_uniqueness_item")
public class ModelUniquenessItemPO extends BasePO<ModelUniquenessItemPO> {

    private static final long serialVersionUID = 1L;

    private Integer modelUniquenessId;

    private Integer modelFieldId;

}
