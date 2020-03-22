package com.eking.momp.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.eking.momp.web.UserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("createUser", UserContext.getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setUpdateFieldValByName("updateUser", UserContext.getUsername(), metaObject);
    }
}
