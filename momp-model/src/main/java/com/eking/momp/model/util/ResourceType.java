package com.eking.momp.model.util;

import lombok.Getter;

@Getter
public enum ResourceType {
    Model("label.model"),
    Entity("label.entity"),
    Layer("label.layer"),
    Uniqueness("label.uniqueness"),
    ModelField("label.modelField"),
    ModelRelation("label.modelRelation"),
    EntityRelation("label.entityRelation"),
    Dimension("label.dimension"),
    RelationType("label.relationType");

    private final String label;

    ResourceType(String label) {
        this.label = label;
    }
}