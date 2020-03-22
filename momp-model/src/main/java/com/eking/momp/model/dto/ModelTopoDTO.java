package com.eking.momp.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.eking.momp.model.po.ModelPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModelTopoDTO implements Serializable {

    private static final long serialVersionUID = 4533168753205277976L;
    private String id;
	
	private Integer realId;

    private String code;

    private String name;
    
    private String description;
    
    private boolean root;//是不是根节点
    
    private boolean editable;//第一层和root的副本可以编辑

    private String iconImage;
    
    private Integer showOrder;
    
    private LocalDateTime createTime;

    private String createUser;

    private LocalDateTime updateTime;

    private String updateUser;
    
    public ModelTopoDTO(ModelPO model) {
    	this.realId = model.getId();
    	this.code = model.getCode();
    	this.name = model.getName();
    	this.description = model.getDescription();
    	this.iconImage = model.getIconImage();
    	this.createTime = model.getCreateTime();
    	this.createUser = model.getCreateUser();
    	this.updateTime = model.getUpdateTime();
    	this.updateUser = model.getUpdateUser();
    }
    
}
