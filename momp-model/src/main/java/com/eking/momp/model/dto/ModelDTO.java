package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ModelDTO {

    private static final long serialVersionUID = -1887490576667363885L;

    private Integer id;

    private String code;

    private String name;

    private String description;

    private String iconImage;
    
    private Integer layerId;
    
    private Integer showOrder;

    private LayerDTO layerDto;

    private boolean sysInit;

    private LocalDateTime createTime;

    private String createUser;

    private LocalDateTime updateTime;

    private String updateUser;
    
    public ModelDTO(ModelPO model) {
        this.id = model.getId();
    	this.code = model.getCode();
    	this.name = model.getName();
    	this.description = model.getDescription();
    	this.iconImage = model.getIconImage();
    	this.layerId = model.getLayerId();
    	this.showOrder = model.getShowOrder();
    	this.setSysInit(model.isSysInit());
    	this.setCreateTime(model.getCreateTime());
    	this.setCreateUser(model.getCreateUser());
    	this.setUpdateTime(model.getUpdateTime());
    	this.setUpdateUser(model.getUpdateUser());
    }
}
