package com.eking.momp.menu.client;

import com.eking.momp.menu.to.DimensionTO;
import com.eking.momp.menu.to.LayerTO;
import com.eking.momp.menu.to.ModelTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "momp-model")
public interface ModelFeignClient {

    @GetMapping("/dimensions")
    List<DimensionTO> listDimensions();

    @GetMapping("/layers")
    List<LayerTO> listLayers();

    @GetMapping("/models")
    List<ModelTO> listModels(@RequestParam(required = false) Integer layerId);

}
