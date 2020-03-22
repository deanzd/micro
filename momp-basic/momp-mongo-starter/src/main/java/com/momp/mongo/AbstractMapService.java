package com.momp.mongo;

import com.eking.momp.common.util.Page;
import com.eking.momp.common.util.Prop;
import com.eking.momp.web.UserContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractMapService {
    @Autowired
    private MongoTemplate mongoTemplate;

    protected List<Map<String, Object>> listObjs(@NonNull String model, @NonNull Query query) {
        initQuery(query);
        List<Map> list = mongoTemplate.find(query, Map.class, model);
        transEntitiesId(list);
        List<Map<String, Object>> entities = uncheckedCast(list);
        return entities;
    }

    protected List<Map<String, Object>> listAllObjs(@NonNull String model) {
        return this.listObjs(model, new Query());
    }

    protected List<Map<String, Object>> listObjs(@NonNull String model, @NonNull Prop... props) {
        Criteria criteria = new Criteria();
        for (Prop prop : props) {
            criteria.and(prop.getColumn()).is(prop.getValue());
        }
        Query query = Query.query(criteria);
        return this.listObjs(model, query);
    }

    protected Page<Map<String, Object>> pageObjs(String model, int pageIndex, int pageSize, String sortBy,
                                                 Boolean asc, Criteria criteria) {
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        Query query = Query.query(criteria).with(pageable);
        if (sortBy != null) {
            if (Boolean.TRUE.equals(asc)) {
                query.with(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, sortBy));
            }
        }
        List<Map<String, Object>> rows = this.listObjs(model, query);
        long total = mongoTemplate.count(query, model);
        int pages = (int) Math.ceil((double) total / (double) pageSize);
        return Page.of(rows, total, pages);
    }

    protected Map<String, Object> saveObj(@NonNull String model, Map<String, Object> params) {
        Map<String, Object> notNullParams = new HashMap<>();
        params.forEach((key, value) -> {
            if (value != null && value.toString().length() > 0) {
                notNullParams.put(key, value);
            }
        });
        notNullParams.put("create_time", LocalDateTime.now());
        notNullParams.put("create_user", UserContext.getUsername());
        notNullParams.put("deleted", false);
        Map<String, Object> entity = mongoTemplate.insert(notNullParams, model);
        transEntityId(entity);
        return entity;
    }

    protected boolean updateObj(@NonNull String model, String id, Map<String, Object> params) {
        Map obj = mongoTemplate.findById(id, Map.class, model);
        if (obj == null) {
            return false;
        }
        obj.put("update_time", LocalDateTime.now());
        obj.put("update_user", UserContext.getUsername());
        params.forEach((key, value) -> {
            if (value == null || value.toString().length() == 0) {
                obj.remove(key);
            } else {
                obj.put(key, value);
            }
        });
        mongoTemplate.save(obj, model);
        return true;
    }

    protected boolean deleteObj(@NonNull String model, @NonNull String id) {
        Map obj = mongoTemplate.findById(id, Map.class, model);
        if (obj == null) {
            return false;
        }
        obj.put("update_time", LocalDateTime.now());
        obj.put("update_user", UserContext.getUsername());
        obj.put("deleted", true);
        mongoTemplate.save(obj, model);
        return true;
    }

    protected Optional<Map<String, Object>> getObjById(@NonNull String model, @NonNull String id) {
        Map obj = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id).and("deleted").is(false)), Map.class,
                model);
        if (obj == null) {
            return Optional.empty();
        }
        transEntityId(obj);
        return Optional.of(obj);
    }

    private void initQuery(@NonNull Query query) {
        query.addCriteria(Criteria.where("deleted").is(false));
    }

    @SuppressWarnings("unchecked")
    private <T> T uncheckedCast(Object obj) {
        return (T) obj;
    }

    private void transEntityId(Map entity) {
        entity.put("id", entity.get("_id").toString());
        entity.remove("_id");
    }

    private void transEntitiesId(List<Map> entities) {
        entities.forEach(this::transEntityId);
    }
}
