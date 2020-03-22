package com.eking.momp.model.client;

import com.eking.momp.common.util.Page;
import com.eking.momp.model.to.EntityIdTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "momp-search")
public interface SearchFeignClient {

    @GetMapping("/entities")
    public Page<EntityIdTO> getEntitiesPage(@RequestParam String keyword,
                                            @RequestParam(required = false) String lastId);

}
