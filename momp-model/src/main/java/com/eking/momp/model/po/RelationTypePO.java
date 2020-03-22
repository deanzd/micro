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
@TableName("relation_type")
public class RelationTypePO extends BasePO<RelationTypePO> {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String text;

    private String reverseText;

}
