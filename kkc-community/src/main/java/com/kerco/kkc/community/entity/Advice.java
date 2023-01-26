package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@ToString
@Data
@TableName("community_advice")
@ApiModel(value = "Advice对象", description = "")
public class Advice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("建议表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发起人id")
    private Long clientId;

    @ApiModelProperty("发起人姓名")
    private String clientName;

    @ApiModelProperty("内容")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
