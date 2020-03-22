package com.eking.momp.model.exception;

import com.eking.momp.model.util.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ResourceInUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResourceType resourceType;

    private Serializable resourceName;

    private ResourceType refResourceType;

    private Serializable refResourceName;

}

