package com.eking.momp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eking.momp.mybatis.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Dean
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("model_relation")
public class ModelRelationPO extends BasePO<ModelRelationPO> {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Integer relationTypeId;

    private Mapping mapping;

    private Integer modelId;

    private Integer targetModelId;

    private Integer modelFieldId;

    private String description;

    @Getter
    public enum Mapping {
        OneToOne("1:1"),
        OneToMany("1:N"),
        ManyToOne("N:1"),
        ManyToMany("N:N");

        private final String text;

        Mapping(String text) {
            this.text = text;
        }
    }

}
