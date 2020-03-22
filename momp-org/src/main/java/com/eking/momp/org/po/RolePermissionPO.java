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
@TableName("role_permission")
public class RolePermissionPO extends BasePO<RolePermissionPO> {

    private static final long serialVersionUID=1L;

    private Integer roleId;

    private Integer permissionId;

}
