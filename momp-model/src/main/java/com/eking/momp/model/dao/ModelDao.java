package com.eking.momp.model.dao;

import com.eking.momp.model.po.ModelPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ModelDao extends BaseMapper<ModelPO> {

}
