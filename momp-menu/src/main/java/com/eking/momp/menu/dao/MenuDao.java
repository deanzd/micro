package com.eking.momp.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eking.momp.menu.po.MenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dean
 * @since 2019-11-21
 */
@Mapper
public interface MenuDao extends BaseMapper<MenuPO> {

}
