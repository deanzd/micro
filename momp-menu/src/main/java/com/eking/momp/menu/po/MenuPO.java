package com.eking.momp.menu.po;

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
@TableName("menu")
public class MenuPO extends BasePO<MenuPO> {

    private static final long serialVersionUID=1L;

    private String name;

    private String path;

    private String icon;

    private Integer parentId;

    private Integer permissionId;

    private String dynamicChildrenCode;

    private Integer showOrder;

}
