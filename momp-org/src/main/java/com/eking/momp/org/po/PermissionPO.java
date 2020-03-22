package com.eking.momp.org.po;

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
 * @since 2019-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("permission")
public class PermissionPO extends BasePO<PermissionPO> {

    private static final long serialVersionUID=1L;

    private String code;

    private String name;

    private String description;

    private String path;

    private String method;

}
