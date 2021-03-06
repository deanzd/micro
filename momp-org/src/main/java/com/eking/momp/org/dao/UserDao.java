package com.eking.momp.org.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eking.momp.org.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dean
 * @since 2019-11-15
 */
@Mapper
public interface UserDao extends BaseMapper<UserPO> {

}
