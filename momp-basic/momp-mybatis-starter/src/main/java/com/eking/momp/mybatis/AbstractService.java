package com.eking.momp.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<M extends BaseMapper<T>, T extends BasePO<T>> {

    @Autowired
    private M mapper;

    protected List<T> listObjs() {
        return mapper.selectList(null);
    }

    protected List<T> listObjs(SFunction<T, ?> column, Object val) {
        return this.lambdaQuery()
                .eq(column, val)
                .list();
    }

    protected List<T> listObjs(SFunction<T, ?> column, Object val, SFunction<T, ?> column1, Object val1) {
        return this.lambdaQuery()
                .eq(column, val)
                .eq(column1, val1)
                .list();
    }

    protected T saveObj(T entity) {
        mapper.insert(entity);
        return entity;
    }

    protected boolean updateObjById(T entity) {
        return SqlHelper.retBool(mapper.updateById(entity));
    }

    protected boolean deleteObj(Serializable id) {
        return SqlHelper.retBool(mapper.deleteById(id));
    }

    protected Optional<T> getObjById(Serializable id) {
        T obj = mapper.selectById(id);
        if (obj == null) {
            return Optional.empty();
        }
        return Optional.of(obj);
    }

    protected Optional<T> getOneObj(SFunction<T, ?> column, Object val) {
        T obj = this.lambdaQuery().eq(column, val).one();
        if (obj == null) {
            return Optional.empty();
        }
        return Optional.of(obj);
    }

    protected Optional<T> getOneObj(SFunction<T, ?> column, Object val, SFunction<T, ?> column1, Object val1) {
        T obj = this.lambdaQuery()
                .eq(column, val)
                .eq(column1, val1)
                .one();
        if (obj == null) {
            Optional.empty();
        }
        return Optional.of(obj);
    }

    protected LambdaQueryChainWrapper<T> lambdaQuery() {
        return new LambdaQueryChainWrapper(this.mapper);
    }


    protected LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return new LambdaUpdateChainWrapper(this.mapper);
    }

}
