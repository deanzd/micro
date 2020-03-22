package com.momp.mongo;

import com.eking.momp.common.util.Page;
import com.eking.momp.common.util.Prop;
import com.eking.momp.web.UserContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends BasePO> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> collectionClass;

    private String collectionName;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
        collectionClass =
                (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        collectionName = collectionClass.getAnnotation(Document.class).value();
    }

    protected T saveObj(T entity) {
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateUser(UserContext.getUsername());
        entity.setDeleted(false);
        return mongoTemplate.insert(entity, collectionName);
    }

    protected T updateObj(T entity) {
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdateUser(UserContext.getUsername());
        return mongoTemplate.save(entity);//save 如果ID存在，则update
    }

    protected boolean deleteObj(@NonNull String id) {
        T obj = this.getObjById(id).orElse(null);
        if (obj == null) {
            return false;
        }
        obj.setDeleted(true);
        this.updateObj(obj);
        return true;
    }

    protected Optional<T> getObjById(@NonNull String id) {
        T obj = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id).and("deleted").is(false)),
                collectionClass, collectionName);
        if (obj == null) {
            return Optional.empty();
        }
        return Optional.of(obj);
    }

    protected List<T> listObjs(Prop... props) {
        Criteria criteria = new Criteria();
        for (Prop prop : props) {
            criteria.and(prop.getColumn()).is(prop.getValue());
        }
        Query query = Query.query(criteria);
        return this.listObjs(query);
    }

    protected List<T> listObjs(@NonNull Query query) {
        initQuery(query);
        return mongoTemplate.find(query, collectionClass, collectionName);
    }

    protected Page<T> pageObjs(int pageIndex, int pageSize, String sortBy, Boolean asc, Criteria criteria) {
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        Query query = Query.query(criteria).with(pageable);
        if (sortBy != null) {
            if (Boolean.TRUE.equals(asc)) {
                query.with(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, sortBy));
            }
        }
        initQuery(query);
        List<T> rows = mongoTemplate.find(query, collectionClass, collectionName);
        long total = mongoTemplate.count(query, collectionName);
        int pages = (int) Math.ceil((double) total / (double) pageSize);
        return Page.of(rows, total, pages);
    }

    private void initQuery(@NonNull Query query) {
        query.addCriteria(Criteria.where("deleted").is(false));
    }
}
