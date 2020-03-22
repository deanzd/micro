package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.LayerDTO;
import com.eking.momp.model.param.LayerParam;

public interface LayerService {

	List<LayerDTO> listWithModels(String keyword);

	List<LayerDTO> list();

	LayerDTO getById(Integer id);

	LayerDTO save(LayerParam param);

	boolean update(Integer id, LayerParam param);

	boolean delete(Integer id);

}
