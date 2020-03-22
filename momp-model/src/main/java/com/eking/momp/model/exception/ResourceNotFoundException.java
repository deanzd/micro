package com.eking.momp.model.exception;

import com.eking.momp.model.util.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8114239930317645452L;

    private ResourceType resourceType;

    private Serializable resouceId;

}