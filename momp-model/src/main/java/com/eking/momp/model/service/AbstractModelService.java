package com.eking.momp.model.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.eking.momp.model.exception.ForbiddenException;
import com.eking.momp.model.exception.ResourceAlreadyExistsException;
import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.mybatis.BasePO;
import com.eking.momp.web.LocaleMessageHolder;
import com.eking.momp.web.UserContext;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Arrays;

public abstract class AbstractModelService<M extends BaseMapper<T>, T extends BasePO<T>> extends AbstractService<M, T> {

    protected void checkResourceExists(String label, SFunction<T, ?> column, Object val) {
        super.getOneObj(column, val).ifPresent(t -> {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        });
    }

    protected void checkResourceExists(String label, SFunction<T, ?> column, Object val,
                                       SFunction<T, ?> column1, Object val1) {

        super.getOneObj(column, val, column1, val1).ifPresent(t -> {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        });
    }

    protected void checkResourceExists(String label, SFunction<T, ?> column, Object val,
                                       SFunction<T, ?> column1, Object val1, SFunction<T, ?> column2, Object val2) {

        T obj = super.lambdaQuery()
                .eq(column, val)
                .eq(column1, val1)
                .eq(column2, val2).one();
        if (obj != null) {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        }
    }

    protected void checkResourceExists(@NonNull Serializable id, String label, SFunction<T, ?> column, Object val) {

        super.getOneObj(column, val).filter(t -> !t.getId().equals(id)).ifPresent(t -> {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        });
    }

    protected void checkResourceExists(@NonNull Serializable id, String label, SFunction<T, ?> column, Object val,
                                       SFunction<T, ?> column1, Object val1) {

        super.getOneObj(column, val, column1, val1).filter(t -> !t.getId().equals(id)).ifPresent(t -> {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        });
    }

    protected void checkResourceExists(@NonNull Serializable id, String label, SFunction<T, ?> column, Object val,
                                       SFunction<T, ?> column1, Object val1, SFunction<T, ?> column2, Object val2) {

        T obj = super.lambdaQuery()
                .ne(T::getId, id)
                .eq(column, val)
                .eq(column1, val1)
                .eq(column2, val2).one();
        if (obj != null) {
            throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage(label));
        }
    }

    protected void checkRole(String... roles) {
        String role = UserContext.getRole();
        if (!Arrays.asList(roles).contains(role)) {
            throw new ForbiddenException();
        }
    }

}
